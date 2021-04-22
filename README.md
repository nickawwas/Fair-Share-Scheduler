# Fair-Share-Scheduler
Simulated a Fair Share Scheduler with Multiple Users using Threads in Java

## Documentation
### Clock 
> Implements Singleton Clock to Count Time, Running on a Separate Thread

### Scheduler
> Schedule Processes using Fair Share Scheduling Algorithm
> - Checks Whether a Process Can Be Moved to the Ready Queue
> - Divides the Quantum Evenly Based on Number of Ready Users Then Among Ready Processes
> - Schedule Ready Process by Changing Process State (Waiting, Ready, Paused, Running, Resume, Finished)
> - Removes Users Without Ready Processes from Ready Queue and Empty Users

### Process Runnable
> Runs Process Using Thread Runnable to Simulate Process Scheduling
> - Arrived Process Change from Waiting to Ready (Waiting -> Ready)
> - Run Ready Processes for Scheduled Quantum (Ready -> Running)
> - Pause Processes After Completion of Quantum if Process Duration Not Met (Running -> Paused)
> - Resume Paused Processes and Run for their Determined Quantum (Paused -> Resume -> Running)
> - Terminate Process if Process Duration Met (Finished)

## Dependencies
Used log4j to Log Output to File and Maven to Build the Java Project