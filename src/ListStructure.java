public class ListStructure {
Node head = null;// baş ve sonu null yaptım
Node tail = null;

void add(int x){
    Node eleman = new Node();// eklenecek olan düğüm
    eleman.data = x;
    eleman.next = null;

    if(head ==  null){// yani düğümüm boş ise
        head = eleman;// baş ve son eleman oldu tek düğüm oluştu
        tail = eleman;
        System.out.println("ilk düğüm eklendi");
    }
    else {
        tail.next = eleman;// son düğümün ardına yeni eleman eklendi
        tail = eleman;
        System.out.println("listenin sonuna yeni elaman eklendi");
    }
}
void print(){
    if(head == null){
        System.out.println("liste boş");
    }
    else {
        Node temp = head;//geçici düğüm tüm düğümlerde gezecek
        System.out.printf("Baş -> ");
        while (temp != null){
            System.out.printf(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.printf("Son.");
    }
}

}
