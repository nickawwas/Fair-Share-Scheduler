# Fair-Share-Scheduler
Simulated a Fair Share Scheduler with Multiple Users using Threads in Java

## Documentation
### Clock 
> Implemented Singleton Clock to Count Time, Running on a Separate Thread

### Scheduler
> Scheduled Processes using Fair Share Scheduling Algorithm
> - Checks Whether a Process Can Be Moved to the Ready Queue
> - Divides the Quantum Evenly Based on Number of Ready Users Then Among Ready Processes
> - Schedule Ready Process by Changing Process State (Waiting, Ready, Paused, Running, Resume, Finished)
> - Removes Users Without Ready Processes from Ready Queue and Empty Users

### Process Runnable
> Stored Process Instance to Simulate Process Scheduling
>
> Scheduler Changes Process State to Simulate Process Scheduling 
> - Arrived Process Changed from Waiting to Ready
> - Run Ready Processes for their Determined Quantum
> - Pause Processes After Completion of Quantum if Process Duration Not Met
> - Terminate Process if Process Duration Met
> - Resume Paused Processes and Run for their Determined Quantum

## Dependencies
Used log4j to Log Output to File and Maven to Build the Java Project