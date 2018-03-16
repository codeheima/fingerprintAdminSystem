package org.ma.device;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ma.app.App;
import org.ma.app.service.CommonService;
import org.ma.db.jdbc.DataObject;
import org.ma.db.jdbc.SessionFactory;
import org.ma.device.util.DeviceUtils;
import org.ma.util.EmptyUtil;

import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

import ma.org.proxy.ano.BeanFactory;

public class FingerDevice {
	private static final Log log = LogFactory.getLog(FingerDevice.class);
	
	private static FingerDevice fd = new FingerDevice();
	//must be 3
	static final int enroll_cnt = 3;

	//the width of fingerprint image
	int fpWidth = 0;
	//the height of fingerprint image
	int fpHeight = 0;
	
	//for verify test
	private byte[] lastRegTemp = new byte[2048];
	//the length of lastRegTemp
	private int cbRegTemp = 0;
	//pre-register template
	private byte[][] regtemparray = new byte[3][2048];
	//finger id
	private int iFid = 1;
	
	private int nFakeFunOn = 1;

	//the index of pre-register function
	private int enroll_idx = 0;
	
	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	
	private byte[] currentTemplate = null;
	
	private int[] templateLen = new int[1];
	
	
	private boolean mbStop = true;
	private boolean isOpen = false;
	private long mhDevice = 0;
	private long mhDB = 0;
	
	private WorkThread workThread = new WorkThread();
	
	private CommonService commonService = null;
	
	public static FingerDevice getInstance() {
		return fd;
	}
	
	
	public void init(){
		commonService  = (CommonService)BeanFactory.getBean("commonService");
	}
	public void reset() {
		isOpen = false;
		fpWidth = 0;
		//the height of fingerpr image
		fpHeight = 0;
		//for verify test
		 lastRegTemp = new byte[2048];
		//the length of lastRegTemp
		cbRegTemp = 0;
		//pre-register template
		  regtemparray = new byte[3][2048];

		//finger id
		  iFid = 1;
		
		  nFakeFunOn = 1;
		//the index of pre-register function
		  enroll_idx = 0;
		
		  imgbuf = null;
		  template = new byte[2048];
		
		  currentTemplate = null;
		
		  templateLen = new int[1];
		
		
		  mbStop = true;
		  mhDevice = 0;
		  mhDB = 0;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public synchronized boolean open() {
		if(isOpen) {
			return true;
		}
		if (0 != mhDevice)
		{
			log.warn("mhDevice is not null:" + mhDevice);
			return false;
		}
		int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
		//Initialize
		cbRegTemp = 0;
		iFid = 1;
		enroll_idx = 0;
		if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
		{
			log.warn("Init failed!");
			return false;
		}
		ret = FingerprintSensorEx.GetDeviceCount();
		if (ret < 0)
		{
			log.warn("No devices connected!");
			close();
			return false;
		}
		if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
		{
			log.warn("Open device fail, ret = " + ret + "!");
			close();
			return false;
		}
		if (0 == (mhDB = FingerprintSensorEx.DBInit()))
		{
			log.warn("Init DB fail, ret = " + ret + "!");
			close();
			return false;
		}
		
		//For ISO/Ansi
		int nFmt = 0;	//Ansi

//		FingerprintSensorEx.DBSetParameter(mhDB,  5010, nFmt);				
		//For ISO/Ansi End
		
		//set fakefun off
		//FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);
		
		byte[] paramValue = new byte[4];
		int[] size = new int[1];
		//GetFakeOn
		//size[0] = 4;
		//FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
		//nFakeFunOn = byteArrayToInt(paramValue);
		
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
		fpWidth = DeviceUtils.byteArrayToInt(paramValue);
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
		fpHeight = DeviceUtils.byteArrayToInt(paramValue);
		//width = fingerprintSensor.getImageWidth();
		//height = fingerprintSensor.getImageHeight();
		imgbuf = new byte[fpWidth*fpHeight];
		
		
		mbStop = false;
		
		workThread = new WorkThread();
	    workThread.start();// 线程启动
	    
	    log.info("Open succ!");
	    isOpen = true;
		return true;
	}
	
	
	public synchronized void close()
	{
		try {
			mbStop = true;
			workThread.interrupt();
			try {		//wait for thread stopping
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (0 != mhDB)
			{
				FingerprintSensorEx.DBFree(mhDB);
				mhDB = 0;
			}
			if (0 != mhDevice)
			{
				FingerprintSensorEx.CloseDevice(mhDevice);
				mhDevice = 0;
			}
			FingerprintSensorEx.Terminate();
			if(workThread.isAlive()) {
				workThread.interrupt();
			}
		}catch(Exception err) {
			log.warn("close device with error");
		}finally {
			this.reset();
		}
	}
	
	
	private class WorkThread extends Thread {
        @Override
        public void run() {
            int ret = 0;
            log.info("workthread  start .");
            try {
	            while (!mbStop) {
	            	templateLen[0] = 2048;
	            	if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen)))
	            	{
	            		if (nFakeFunOn == 1)
	                	{
	                		byte[] paramValue = new byte[4];
	        				int[] size = new int[1];
	        				size[0] = 4;
	        				int nFakeStatus = 0;
	        				//GetFakeStatus
	        				ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
	        				nFakeStatus = DeviceUtils.byteArrayToInt(paramValue);
	        				System.out.println("ret = "+ ret +",nFakeStatus=" + nFakeStatus);
	        				if (0 == ret && (byte)(nFakeStatus & 31) != 31)
	        				{
	        					log.error("WorkThread end : Is a fake-finer?");
	        					break;
	        				}
	                	}
	            		
	               // 	OnCatpureOK(imgbuf);
	               // 	OnExtractOK(template, templateLen[0]);
	                	currentTemplate = Arrays.copyOfRange(template , 0,templateLen[0]);
	                	
	                
	                	saveCurrentFinger();
	            	}
	                Thread.sleep(500);
	            }//end while
            } catch (InterruptedException e) {
            	log.info("WorkThread end : interrupted...");
            }
            log.info("WorkThread over.");
        }//end run()

    }
	
	/**
	 * 保存
	 */
	private void saveCurrentFinger() {
		try {
			//img
			System.out.println(template + ","+ templateLen[0]);
		//	SaveImage.saveImage(template, templateLen[0]);
			
			System.out.println(currentTemplate);  //
			
			List<DataObject> list = commonService.executeQuery("zfdb", "select * from CUSTOMER_CURRENT");
			if(EmptyUtil.isEmptyList(list)) {
				log.warn("no currentData to save, test to throw Exception .");
		//		throw new NullPointerException("no currentData to save..");
			}
			DataObject cus = list.get(0);
			if(cus.getObject("zf1") == null) {
				cus.setValue("zf1", currentTemplate);
			}else if(cus.getObject("zf2") == null) {
				cus.setValue("zf2", currentTemplate);
			}else if(cus.getObject("zf3") == null) {
				cus.setValue("zf3", currentTemplate);
			}else {
				log.info("enough fingerprint . ");
			}
			commonService.update("zfdb.customerCurrent", cus);
			//currentTemplate
			
		}catch(Exception err) {
			throw new RuntimeException(err);
		}finally {
			SessionFactory.closeSession();
		}
	}
	
	
	public boolean compare(byte[] a,byte[] b) {
		 int matchResult =  FingerprintSensorEx.DBMatch(mhDB, b, a);
		 log.info("device compare match: " + matchResult);
		 if(matchResult >App.INSTANCE.getCompareSuccess()) {
			 return true;
		 }else {
			 return false;
		 }
		 
	}
	
	public byte[] merge(byte[] a1,byte[] a2,byte[] a3) {
		byte [] bbb = new byte[2048];
		int [] bbLen = new int[] {2048};
		
		int status = FingerprintSensorEx.DBMerge(mhDB, a1, a2, a3, bbb, bbLen);
		if(0 == status) {
			return Arrays.copyOfRange(bbb , 0,  bbLen[0]);
		}else {
			return null;
		}
	}

	private FingerDevice() {
		
	}
	
	
	
	
}
