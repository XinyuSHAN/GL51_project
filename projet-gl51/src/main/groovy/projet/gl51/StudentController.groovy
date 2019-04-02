package projet.gl51

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus


@Controller("/student")
class StudentController {

   @Get("/")
   List<Student> index() {
      	[new Student (firstname: "Xinyu", lastname:"SHAN"), new Student (firstname: "Linzi", lastname:"MA")]
   }
}