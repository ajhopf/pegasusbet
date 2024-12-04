package webcrawler

//import com.fasterxml.jackson.annotation.JsonAutoDetect
//
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class RaceCourse {
    String name

    static constraints = {
        name unique: true, nullable: false
    }


    @Override
    public String toString() {
        return "RaceCourse{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
