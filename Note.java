
class Note {

    String title;
    String fileName;
    String content;
    String ownerName;

    public Note(
        String title,
        String fileName,
        String content,
        String ownerName
    ) {
        this.title = title;
        this.fileName = fileName;
        this.content = content;
        this.ownerName = ownerName;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }
}