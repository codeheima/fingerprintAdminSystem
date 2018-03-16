package ma.org.proxy;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {
	public static <T,V>  void optMapEntry(Map<T,V> map,IOptMapEntry<T,V> optMapEntry){
		 Set<Map.Entry<T, V>> set = map.entrySet();
		 for (Iterator<Map.Entry<T, V>> it = set.iterator(); it.hasNext();) {
            Map.Entry<T, V> entry = (Map.Entry<T, V>) it.next();
       //     entry.getKey() , entry.getValue());
            optMapEntry.opt(entry.getKey(), entry.getValue());
        }
	}
	
	
	public interface IOptMapEntry<T,V>{
		public  void opt(T key, V value);
	}
}
