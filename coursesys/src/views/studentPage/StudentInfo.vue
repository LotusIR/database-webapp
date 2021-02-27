<template>
    <div style="text-align: center">
        <el-card class="box-card" style="width: 50%;display: inline-block">
            <div>学号: {{student.id}}</div>
            <div>姓名: {{student.name}}</div>
            <div>性别: {{student.sex}}</div>
            <div>年龄: {{student.age}}</div>
            <div>学院: {{student.dept}}</div>
        </el-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                student: {
                    id: "",
                    name: "",
                    sex: "",
                    age: "",
                    dept: ""
                }
            }
        },
        methods: {

        },
        beforeMount() {
            if (!window.sessionStorage.getItem('studentId') || window.sessionStorage.getItem('studentId') == 'admin') {
                    this.$message.error('请登录学生帐号')
                    this.$router.replace("/")
                    return null
            }
            this.getRequest("/getStudentInfo", {
                studentId: window.sessionStorage.getItem('studentId')
            }).then(resp => {
                if (resp.code == 200) {
                    this.student.id = resp.obj.studentId
                    this.student.name = resp.obj.studentName
                    this.student.sex = resp.obj.studentSex
                    this.student.age = resp.obj.studentAge
                    this.student.dept = resp.obj.studentDept

                }
            })
        },
        return: "StudentInfo"
    }
</script>

<style scoped>

</style>