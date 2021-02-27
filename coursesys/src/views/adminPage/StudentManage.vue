<template>
    <div>
        <div style="text-align: center">学生信息</div>
        <div class="tableContainer">
            <el-table :data="students" stripe style="width: 100%" border>
                <el-table-column prop="studentId" label="学号" min-width="70">
                </el-table-column>
                <el-table-column prop="studentName" label="姓名" min-width="100">
                </el-table-column>
                <el-table-column prop="studentSex" label="性别" min-width="50">
                </el-table-column>
                <el-table-column prop="studentAge" label="年龄" min-width="100">
                </el-table-column>
                <el-table-column prop="studentDept" label="学院" min-width="80">
                </el-table-column>
                <el-table-column fixed="right" label="操作" min-width="100">
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="deleteStudent(scope.$index, students)" type="text"
                            size="small">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button size="small" @click="studentFormVisible = true">+</el-button>


        </div>


        <el-dialog title="添加学生" :visible.sync="studentFormVisible"  width="550px">
            <el-form :model="newStudent" ref='newStudent' :rules="rules">
                <el-form-item label="学号"  label-width="120px" prop="studentId">
                    <el-input v-model="newStudent.studentId" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="姓名"  label-width="120px" prop="studentName">
                    <el-input v-model="newStudent.studentName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别"  label-width="120px" prop="studentSex">
                    <el-input v-model="newStudent.studentSex" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄"  label-width="120px" prop="studentAge">
                    <el-input v-model="newStudent.studentAge" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学院"  label-width="120px" prop="studentDept">
                    <el-input v-model="newStudent.studentDept" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div class="tip">用户名为学号，初始密码为123456</div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="studentFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addStudent('newStudent')">添 加</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                students: [],
                newStudent: {
                    studentId: "",
                    studentName: "",
                    studentSex: "",
                    studentAge: "",
                    studentDept: ""
                },
                studentFormVisible: false,
                rules: {
                    studentId: [{
                        required: true,
                        message: '请输入学号',
                        trigger: 'blur'
                    }],
                    studentName: [{
                        required: true,
                        message: '请输入姓名',
                        trigger: 'blur'
                    }],
                    studentSex: [{
                        required: true,
                        message: '请输入性别',
                        trigger: 'blur'
                    }],
                    studentAge: [{
                        required: true,
                        message: '请输入年龄',
                        trigger: 'blur'
                    }],
                    studentDept: [{
                        required: true,
                        message: '请输入学院',
                        trigger: 'blur'
                    }]
                }
            }
        },
        methods: {
            getStudents() {
                let studentsList = new Array()
                this.getRequest("/getAllStudents", null).then(resp => {
                    if (resp.code == 200) {
                        for (var i in resp.obj)
                            studentsList.push(resp.obj[i])
                    }
                })
                return studentsList
            },
            deleteStudent(index, table) {
                let delStudentId = table[index].studentId
                this.postRequest("/deleteStudent", {
                    studentId: delStudentId,
                    forced: false
                }).then(resp => {
                    if (resp.code == 200) {
                        this.students = this.getStudents()
                    } else if (resp.code == 501) {
                        this.$confirm('该学生有选课记录，是否一并删除?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning',
                            center: true
                        }).then(() => {
                            this.postRequest("/deleteStudent", {
                                studentId: delStudentId,
                                forced: true
                            }).then(resp=>{
                                if (resp.code == 200) {
                                    this.students = this.getStudents()
                                }
                            })
                        }).catch(() => {
                            this.$message({
                                type: 'info',
                                message: '已取消删除'
                            });
                        });
                    }
                })
            },
            addStudent(student) {
                this.$refs[student].validate((valid) => {
                    if (valid) {
                        this.postRequest("/createStudent",this.newStudent).then(resp=>{
                            if (resp.code == 200) {
                                this.students = this.getStudents()
                                this.newStudent = {}
                                this.studentFormVisible = false
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
            this.students = this.getStudents()
        },
        return: "StudentManage"
    }
</script>

<style scoped>
    .el-input {
        width: 300px
    }

    .tip {
        font-size: xx-small;
        color: rgb(252, 55, 55);
        text-align: center;
    }
</style>