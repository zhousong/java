package enums;

public class Enums {
	public static void main(String[] args) {
		System.out.println(Animal.Cat.id);
		System.out.println(Animal.Cat.tag());

		switch (getKey()) {
		case Dog:
			System.out.println(Animal.Dog.tag);
			break;
		case Cat:
			System.out.println(Animal.Cat.tag);
			break;
		case Mouse:
			System.out.println(Animal.Mouse.tag);
			break;
		default:
			System.out.println("None");
			break;
		}
	}

	private static Animal getKey() {
		return Animal.Mouse;
	}

	enum Animal {
		Dog(1, "dog"), Cat(2, "cat"), Mouse(3, "mouse");

		private int id;
		private String tag;

		private Animal(int id, String name) {
			this.id = id;
			this.tag = name;
		}

		public int id() {
			return id;
		}

		public String tag() {
			return tag;
		}
	}
}
