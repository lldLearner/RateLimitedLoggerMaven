Notes -> 

Initally I was thinking to go with AtomicInteger approach, the drawbacks that I found were as follows -> 

Lets say there are Threads T1 and T2, now get() will is suppose accessed by T1 then immeddiately it got context switched the same value will be accessed by T2, which can lead to the race condn.
I tried handling it with compareAndSet(int expect, int update) which could have kind of handled the race condition but for token acquisiton there seemed to be a need to take the lock. 

After I decided to discard this approach, I decided to go with object level locking i.e. synchronized methods and wait() and notifyAll() mechanism. 

But I still think that synchronizeed methods make the code as good as single threaded, I may need some suggestions on this approach.

This is how the output looked like - 
```
Message logged by thread pool-1-thread-1 messageNumber 0
Message logged by thread pool-1-thread-2 messageNumber 1
Message logged by thread pool-1-thread-3 messageNumber 2
Message logged by thread pool-1-thread-2 messageNumber 4
Message logged by thread pool-1-thread-1 messageNumber 3
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 6 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 5 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 7 went into waiting mode![0m
Message logged by thread pool-1-thread-2 messageNumber 6
Message logged by thread pool-1-thread-2 messageNumber 8
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 6 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 7
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 7 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 5 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 5
Message logged by thread pool-1-thread-1 messageNumber 10
Message logged by thread pool-1-thread-2 messageNumber 9
Message logged by thread pool-1-thread-1 messageNumber 12
Message logged by thread pool-1-thread-1 messageNumber 14
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 15 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 11 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 13 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 15 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 15
Message logged by thread pool-1-thread-2 messageNumber 13
Message logged by thread pool-1-thread-3 messageNumber 11
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 13 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 11 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 17
Message logged by thread pool-1-thread-1 messageNumber 16
Message logged by thread pool-1-thread-2 messageNumber 19
Message logged by thread pool-1-thread-3 messageNumber 18
Message logged by thread pool-1-thread-2 messageNumber 21
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 23 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 20 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 22 went into waiting mode![0m
```
