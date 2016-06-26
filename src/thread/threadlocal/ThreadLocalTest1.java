package thread.threadlocal;

public class ThreadLocalTest1 {

    public static void main(String[] args) throws Exception{
        ThreadLocal<Person> local = new ThreadLocal<>();
        final Person person = new Person();
        person.setId("ID");
        person.setName("Hello");
        local.set(person);
        final Person p = local.get();
        System.out.println(p);
        System.out.println("--------update name---------");
        p.setName("World");
        System.out.println(local.get());
        System.out.println("--------delete--------");
        local.remove();
        System.out.println(local.get());

    }
    static class Person {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Person{");
            sb.append("id='").append(id).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
