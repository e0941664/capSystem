import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';
import { FormErrors } from './FormErrors';


class LecturerCourse extends Component {
    defaultLecturer = {
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = {
            lecturerCourses: [], 
            lecturer: this.defaultLecturer,
            error: '',
        };
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/lecturer/course/${this.props.match.params.id}`)
            .then(response => response.json())
            .then(data => this.setState({lecturerCourses: data}));

        if(this.state.lecturerCourses !== null){
            const currentLecturer = await (await fetch(`/admin/lecturer/${this.props.match.params.id}`)).json();
            this.setState({lecturer: currentLecturer});
        }
      }

      async remove(id) {
        await fetch(`/admin/lecturer/course/${this.state.lecturer.username}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.status == null){
                this.setState({lecturerCourses: data, error:""});
            }
            else{
                this.setState({error: "Course only has 1 Lecturer"})
            }
        });
    };
    
      
    render() {
        const {lecturerCourses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }



        const lecturerCourseList = lecturerCourses.map(lecturerCourseDetail => {
            return (
                <tr key= {lecturerCourseDetail.id}>
                    <td>
                        {lecturerCourseDetail.course.name}
                    </td>
                    <td>
                        {lecturerCourseDetail.startDate}
                    </td>
                    <td>
                        {lecturerCourseDetail.endDate}
                    </td>
                    <td>
                        {lecturerCourseDetail.size}/{lecturerCourseDetail.maxSize}
                    </td>

                    <td>
                        <ButtonGroup>
                            {/* <Button size="sm" color="primary" tag={Link} to={"/admin/student/course" + studentCourseDetail.studentId}>Edit</Button> */}
                            <Button size="sm" color="danger" onClick={() => this.remove(lecturerCourseDetail.id)}>Drop Lecturer</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container fluid>
                <div className="float-right">
                    {/* <Button color="success" tag={Link} to={`/admin/student/course/${this.state.student.username}/new`}>Enroll Student into a new Course</Button> */}
                </div>
                <h3>{this.state.lecturer.name}'s Courses</h3>
                <Table className='text-center'>
                    <thead>
                        <tr>
                            <th>
                                Course Name
                            </th>
                            <th>
                                Course Start Date
                            </th>
                            <th>
                                Course End Date
                            </th>
                            <th>
                                Current Enrolment
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {lecturerCourseList}
                    </tbody>
                </Table>
                <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                <Button outline color="success" tag={Link} to={`/admin/lecturer/course/${this.state.lecturer.lecturerId}/new`}>Add Lecturer into a new Course</Button>
            </Container>
        );

    }
    

}

export default LecturerCourse;