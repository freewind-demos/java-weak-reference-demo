Java Weak Reference Demo
========================

Understand weak reference.

- weak reference: When Jvm runs `gc`, if there is any weak reference, they will be released, even if the heap memory is not full.
- soft reference: Soft references are only released when heap is full when GC

Run
---

Run `Hello.java` in your IDE, and set the vm options to:

- `-XX:+PrintGC`: print gc information in console

Since we create very small amount of objects in this demo, we don't need to specify the heap size like we do in other demos.

Log in console
--------------

The output may like:

```
cache size: 10
[GC (System.gc())  5252K->824K(251392K), 0.0013236 secs]
[Full GC (System.gc())  824K->653K(251392K), 0.0066789 secs]
# Released weak references: 0
cache size: 20
[GC (System.gc())  3274K->789K(251392K), 0.0006443 secs]
[Full GC (System.gc())  789K->615K(251392K), 0.0071458 secs]
# Released weak references: 10
cache size: 20
[GC (System.gc())  3237K->743K(251392K), 0.0003727 secs]
[Full GC (System.gc())  743K->615K(251392K), 0.0064726 secs]
# Released weak references: 10
cache size: 20
[GC (System.gc())  1925K->743K(251392K), 0.0003508 secs]
[Full GC (System.gc())  743K->615K(251392K), 0.0024689 secs]
# Released weak references: 10
cache size: 20
[GC (System.gc())  1925K->711K(251392K), 0.0004487 secs]
[Full GC (System.gc())  711K->615K(251392K), 0.0023783 secs]
# Released weak references: 10
```

You can see the `Released weak references` is great then 0 for each `GC` after the first time, even if there is still a very large amount of Heap size.

