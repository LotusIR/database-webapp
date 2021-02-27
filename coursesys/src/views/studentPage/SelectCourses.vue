<template>
    <div style="text-align: center">
        <div>已修课程</div>
        <div class="tableContainer">
            <el-table :data="courseHistories" stripe>
                <el-table-column prop="courseId" label="课程号" width="120">
                </el-table-column>
                <el-table-column prop="courseName" label="课程名" width="120">
                </el-table-column>
                <el-table-column prop="grade" label="成绩" width="80">
                </el-table-column>
            </el-table>
        </div>
        <div>可选课程</div>
        <div class="tableContainer">
            <el-table :data="validCourses" stripe>
                <el-table-column prop="courseId" label="课程号" width="120">
                </el-table-column>
                <el-table-column prop="courseName" label="课程名" width="120">
                </el-table-column>
                <el-table-column prop="courseCredit" label="学分" width="60">
                </el-table-column>
                <el-table-column prop="courseDept" label="开课学院" width="120">
                </el-table-column>
                <el-table-column prop="courseTeacher" label="任课老师">
                </el-table-column>
            </el-table>
        </div>
        <div>已选课程</div>
        <div class="tableContainer">
            <el-table :data="hasCourses" stripe>
                <el-table-column prop="courseId" label="课程号" width="120">
                </el-table-column>
                <el-table-column prop="courseName" label="课程名" width="120">
                </el-table-column>
                <el-table-column prop="courseCredit" label="学分" width="60">
                </el-table-column>
                <el-table-column prop="courseDept" label="开课学院" width="120">
                </el-table-column>
                <el-table-column prop="courseTeacher" label="任课老师">
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-input v-model="courseId" style="width: auto" placeholder="请输入课程号"></el-input>
            <el-button type="primary" @click="selectCourse">选课</el-button>
            <el-button type="primary" @click="dropCourse">退课</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                validCourses: [],
                hasCourses: [],
                courseHistories: [],
                courseId: null,
            }
        },
        methods: {
            getValidCourses() {
                let courseList = new Array()
                this.getRequest("/validCourses", {
                    "studentId": window.sessionStorage.getItem('studentId')
                }).then(resp => {
                    for (var i in resp.obj) {
                        courseList.push(resp.obj[i])
                    }
                })
                return courseList
            },
            getHasCourses() {
                let courseList = new Array()
                this.getRequest("/hasCourses", {
                    "studentId": window.sessionStorage.getItem('studentId')
                }).then(resp => {
                    for (var i in resp.obj) {
                        courseList.push(resp.obj[i])
                    }
                })
                return courseList
            },
            selectCourse() {
                if (!window.sessionStorage.getItem('studentId')) {
                    this.$message.error('请先登录')
                }
                else if (!this.courseId || this.courseId == "") {
                    this.$message.error('课程号不能为空')
                }
                else {
                    this.postRequest("/selectCourse",{studentId: window.sessionStorage.getItem('studentId'),courseId: this.courseId}).then(resp=>{
                        if (resp.code == 200) {
                            this.validCourses = this.getValidCourses()
                            this.hasCourses = this.getHasCourses()
                        }
                        else {
                            this.$message.error(resp.message)
                        }
                    })
                }
            },
            dropCourse() {
                if (!window.sessionStorage.getItem('studentId')) {
                    this.$message.error('请先登录')
                }
                else if (!this.courseId || this.courseId == "") {
                    this.$message.error('课程号不能为空')
                }
                else {
                    this.postRequest("/dropCourse",{studentId: window.sessionStorage.getItem('studentId'),courseId: this.courseId}).then(resp=>{
                        if (resp.code == 200) {
                            this.validCourses = this.getValidCourses()
                            this.hasCourses = this.getHasCourses()
                        }
                        else {
                            this.$message.error(resp.message)
                        }
                    })
                }
            }
        },
        beforeMount() {
             if (!window.sessionStorage.getItem('studentId') || window.sessionStorage.getItem('studentId') == 'admin') {
                    this.$message.error('请登录学生帐号')
                    this.$router.replace("/")
                    return null
            }
            this.validCourses = this.getValidCourses()
            this.hasCourses = this.getHasCourses()
            this.getRequest("/getCourseHistory",{studentId: window.sessionStorage.getItem('studentId')}).then(resp=>{
                if (resp.code==200) {
                    this.courseHistories = resp.obj
                }
            })
        },
        return: "SelectCourses"
    }
</script>

<style scoped>
    .el-table {
        border-radius: 15px;
        width: fit-content;
        box-shadow: 0 0 10px #cac6c6;
        display: inline-block;
    }

    .tableContainer {
        text-align: center;
    }
</style>