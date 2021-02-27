<template>
    <div style="display: flex">
        <div style="display: inline-block;width: 10%;vertical-align: middle"></div>
        <div style="display: inline-block;width: 35%;vertical-align: top">
            <div style="text-align: center">所有课程</div>
            <div class="tableContainer">
                <el-table :data="courses" stripe style="width: 100%">
                    <el-table-column prop="courseId" label="课程号" min-width="70">
                    </el-table-column>
                    <el-table-column prop="courseName" label="课程名" min-width="100">
                    </el-table-column>
                    <el-table-column prop="courseCredit" label="学分" min-width="50">
                    </el-table-column>
                    <el-table-column prop="courseDept" label="开课学院" min-width="100">
                    </el-table-column>
                    <el-table-column prop="courseTeacher" label="任课老师" min-width="80">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="100">
                        <template slot-scope="scope">
                            <el-button @click.native.prevent="getStudentList(scope.$index, courses)" type="text"
                                size="small">
                                查询
                            </el-button>
                            <el-button @click.native.prevent="deleteCourse(scope.$index, courses)" type="text"
                                size="small">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-button size="small" @click="courseFormVisible = true">+</el-button>


            </div>
        </div>
        <div style="display: inline-block;width: 10%;vertical-align: top"></div>
        <div style="display: inline-block;width: 35%">
            <div style="text-align: center">选修学生</div>
            <div class="tableContainer">
                <el-table :data="students" stripe style="width: 100%">
                    <el-table-column prop="studentId" label="学号" min-width="80">
                    </el-table-column>
                    <el-table-column prop="studentName" label="姓名" min-width="100">
                    </el-table-column>
                    <el-table-column prop="grade" label="成绩" min-width="50">
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" min-width="60">
                        <template slot-scope="scope">
                            <el-button @click.native.prevent="enterGrade(scope.$index, students)" type="text"
                                size="small">
                                输入成绩
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <div style="display: inline-block;width: 10%;"></div>

        <el-dialog title="新建课程" :visible.sync="courseFormVisible"  width="550px">
            <el-form :model="newCourse" ref='newCourse' :rules="rules">
                <el-form-item label="课程号"  label-width="120px" prop="courseId">
                    <el-input v-model="newCourse.courseId" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="课程名"  label-width="120px" prop="courseName">
                    <el-input v-model="newCourse.courseName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学分"  label-width="120px" prop="courseCredit">
                    <el-input v-model="newCourse.courseCredit" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学院"  label-width="120px" prop="courseDept">
                    <el-input v-model="newCourse.courseDept" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="任课老师"  label-width="120px" prop="courseTeacher">
                    <el-input v-model="newCourse.courseTeacher" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="courseFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addCourse('newCourse')">新 建</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                courses: [],
                students: [],
                curCourseId: 0,
                courseFormVisible: false,
                newCourse: {
                    courseId: null,
                    courseName: "",
                    courseCredit: null,
                    courseDept: "",
                    courseTeacher: ""
                },
                rules: {
                    courseId: [{
                        required: true,
                        message: '请输入课程号',
                        trigger: 'blur'
                    }],
                    courseName: [{
                        required: true,
                        message: '请输入课程名',
                        trigger: 'blur'
                    }],
                    courseCredit: [{
                        required: true,
                        message: '请输入课程学分',
                        trigger: 'blur'
                    }],
                    courseDept: [{
                        required: true,
                        message: '请输入学院',
                        trigger: 'blur'
                    }],
                    courseTeacher: [{
                        required: true,
                        message: '请输入任课教师',
                        trigger: 'blur'
                    }],
                }
            }
        },
        methods: {
            deleteCourse(index, table) {
                let delCourseId = table[index].courseId
                this.postRequest("/deleteCourse", {
                    courseId: delCourseId
                }).then(resp => {
                    if (resp.code == 200) {
                        this.courses = this.getCourses()
                    }
                })
            },
            getStudentList(index, table) {
                this.curCourseId = table[index].courseId
                this.refreshStudentList()

            },
            enterGrade(index, table) {
                let studentId = table[index].studentId
                this.$prompt('请输入成绩', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[1-9][0-9]?$|^[0]$|^[1][0][0]$/,
                    inputErrorMessage: '请输入正确的成绩'
                }).then(({
                    value
                }) => {
                    console.log(studentId)
                    this.postRequest("/updateGrade", {
                        studentId: studentId,
                        courseId: this.curCourseId,
                        grade: value
                    }).then(resp => {
                        if (resp.code == 200) {
                            this.refreshStudentList()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            getCourses() {
                let coursesList = new Array()
                this.getRequest("/getAllCourses", null).then(resp => {
                    if (resp.code == 200) {
                        for (var i in resp.obj)
                            coursesList.push(resp.obj[i])
                    }
                })
                return coursesList
            },
            refreshStudentList() {
                this.postRequest("/getStudentList", {
                    courseId: this.curCourseId
                }).then(resp => {
                    if (resp.code == 200) {
                        this.students = resp.obj
                    }
                })
            },
            addCourse(course) {
                this.$refs[course].validate((valid) => {
                    if (valid) {
                        this.postRequest("/createCourse",this.newCourse).then(resp=>{
                            if (resp.code == 200) {
                                this.courses = this.getCourses()
                                this.newCourse = {}
                                this.courseFormVisible = false
                            }
                        })
                    } else {
                        this.$message.error("请输入所有字段")
                        return false;
                    }
                })
            }
        },
        beforeMount() {
            this.courses = this.getCourses()
        },
        return: "CourseManage"
    }
</script>

<style scoped>
    .el-table {
        border-radius: 15px;
        width: fit-content;
        box-shadow: 0 0 10px #cac6c6;
        display: inline-block;
    }

    /* .tableContainer {
        text-align: center;
    } */
    .el-input {
        width: 300px
    }
</style>
