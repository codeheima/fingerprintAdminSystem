/**

	var task = {
	    run: function(){
	        $('#clock').update(new Date().getTime());
	    },
	    interval: 1000 //1 second
	}
	$CTJ.TaskManager.start(task);

*/

$CTJ.util.TaskRunner = function(interval){
    interval = interval || 10;
    var tasks = [], 
        removeQueue = [],
        id = 0,
        running = false,

        // private
        stopThread = function(){
                running = false;
                clearInterval(id);
                id = 0;
            },

        // private
        startThread = function(){
                if(!running){
                    running = true;
                    id = setInterval(runTasks, interval);
                }
            },

        // private
        removeTask = function(t){
                removeQueue.push(t);
                if(t.onStop){
                    t.onStop.apply(t.scope || t);
                }
            },
            
        // private
        runTasks = function(){
                var rqLen = removeQueue.length,
                        now = new Date().getTime();                                             
            
                if(rqLen > 0){
                    for(var i = 0; i < rqLen; i++){
                        tasks.remove(removeQueue[i]);
                    }
                    removeQueue = [];
                    if(tasks.length < 1){
                        stopThread();
                        return;
                    }
                }               
                for(var i = 0, t, itime, rt, len = tasks.length; i < len; ++i){
                    t = tasks[i];
                    itime = now - t.taskRunTime;
                    if(t.interval <= itime){
                        rt = t.run.apply(t.scope || t, t.args || [++t.taskRunCount]);
                        t.taskRunTime = now;
                        if(rt === false || t.taskRunCount === t.repeat){
                            removeTask(t);
                            return;
                        }
                    }
                    if(t.duration && t.duration <= (now - t.taskStartTime)){
                        removeTask(t);
                    }
                }
            };

    
/**
     * Starts a new task.
     * @method start
     * @param {Object} task 
A config object that supports the following properties:


     * 
run : Function
The function to execute each time the task is invoked. The
     * function will be called at each interval and passed the args argument if specified, and the
     * current invocation count if not.


     * 
If a particular scope (this reference) is required, be sure to specify it using the scope argument.


     * 
Return false from this function to terminate the task.


     * 
interval : Number
The frequency in milliseconds with which the task
     * should be invoked.

     * 
args : Array
(optional) An array of arguments to be passed to the function
     * specified by run. If not specified, the current invocation count is passed.

     * 
scope : Object
(optional) The scope (this reference) in which to execute the
     * run function. Defaults to the task config object.

     * 
duration : Number
(optional) The length of time in milliseconds to invoke
     * the task before stopping automatically (defaults to indefinite).

     * 
repeat : Number
(optional) The number of times to invoke the task before
     * stopping automatically (defaults to indefinite).

     * 

     * 
Before each invocation, $CTJ injects the property taskRunCount into the task object so
     * that calculations based on the repeat count can be performed.


     * @return {Object} The task
     */
    this.start = function(task){
        tasks.push(task);
        task.taskStartTime = new Date().getTime();
        task.taskRunTime = 0;
        task.taskRunCount = 0;
        startThread();
        return task;
    };

    
/**
     * Stops an existing running task.
     * @method stop
     * @param {Object} task The task to stop
     * @return {Object} The task
     */
    this.stop = function(task){
        removeTask(task);
        return task;
    };

    
/**
     * Stops all tasks that are currently running.
     * @method stopAll
     */
    this.stopAll = function(){
        stopThread();
        for(var i = 0, len = tasks.length; i < len; i++){
            if(tasks[i].onStop){
                tasks[i].onStop();
            }
        }
        tasks = [];
        removeQueue = [];
    };
};


$CTJ.TaskManager = new $CTJ.util.TaskRunner();


