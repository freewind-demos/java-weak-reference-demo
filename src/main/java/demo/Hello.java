package demo;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hello {

    private static ReferenceQueue<User> releaseLog = new ReferenceQueue<>();

    private static ArrayList<WeakReference<User>> cache = new ArrayList<WeakReference<User>>() {
        @Override
        public boolean add(WeakReference<User> userWeakReference) {
            removeReleasedItems();
            return super.add(userWeakReference);
        }
        private void removeReleasedItems() {
            for (int i = this.size() - 1; i >= 0; i--) {
                WeakReference<User> item = this.get(i);
                if (item.get() == null) {
                    this.remove(i);
                }
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            List<User> users = generateUsers(10);
            for (User user : users) {
                WeakReference<User> reference = new WeakReference<>(user, releaseLog);
                cache.add(reference);
            }

            System.out.println("cache size: " + cache.size());

            System.gc();

            printReleasedUsers(releaseLog);

            Thread.sleep(1000);
        }
    }

    private static void printReleasedUsers(ReferenceQueue<User> queue) throws InterruptedException {
        int timeout = 10;
        int releaseCount = 0;
        while (true) {
            Reference<? extends User> ref = queue.remove(timeout);
            if (ref == null) {
                break;
            }
            releaseCount += 1;
        }
        System.out.println("# Released weak references: " + releaseCount);
    }

    private static List<User> generateUsers(int count) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(new User("" + i));
        }
        return users;
    }

}

class User {
    final String id = UUID.randomUUID().toString();
    final String name;
    public User(String name) {
        this.name = name;
    }
}
