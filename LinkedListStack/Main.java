package LinkedListStack;


class Main {
    public static void main(String[] args){
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("First Element");
        stack.push("Second Elemenet");
        System.out.println(stack.top());
        stack.pop();
        System.out.println(stack.top());
    }
}