package com.ttreport.datacenter.sync

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

trait ConcurrentResource<T>
{
    private  Map<T, Lock> locks = [:]
    private  final long allowedLockCount = 1000L

    public <V> V withLockFor(T id, Closure<V> closure) throws Exception
    {
        Lock lock = locks.getOrDefault(id, null)
        if(!lock){
            if(locks.size() == allowedLockCount){
                throw new Exception("Cannot acquire a new lock")
            }
            lock = new ReentrantLock()
            locks.put(id,lock)
        }
        lock.lock()
        try{
            closure()
        }
        finally {
            lock.unlock()
        }
    }
}