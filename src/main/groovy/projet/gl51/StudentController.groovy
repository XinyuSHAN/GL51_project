package projet.gl51

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get


@Controller("/student")
class StudentController {

    @Get("/")
    List<Student> index() {
        [new Student(firstname: "linzi", lastname:"MA"), new Student(firstname: "Xinyu", lastname:"SHAN")]
    }
}