<template>
    <div style="text-align: center">
        <div>选修历史</div>
        <div class="tableContainer">
            <el-table :data="courseHistories" stripe>
                <el-table-column prop="courseId" label="课程号" width="120">
                </el-table-column>
                <el-table-column prop="courseName" label="课程名" width="120">
                </el-table-column>
                <el-table-column prop="courseCredit" label="学分" width="60">
                </el-table-column>
                <el-table-column prop="grade" label="成绩" width="120">
                </el-table-column>
                <el-table-column prop="courseTeacher" label="任课老师">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                courseHistories: []
            }
        },
        beforeMount() {
            if (!window.sessionStorage.getItem('studentId') || window.sessionStorage.getItem('studentId') == 'admin') {
                    this.$message.error('请登录学生帐号')
                    this.$router.replace("/")
                    return null
            }
            this.getRequest("/getCourseHistory",{studentId: window.sessionStorage.getItem('studentId')}).then(resp=>{
                if (resp.code==200) {
                    this.courseHistories = resp.obj
                }
            })
        },
        return: 'CourseHistory'
    }
</script>

<style scoped>
    .tableContainer {
        text-align: center;
    }

    .el-table {
        border-radius: 15px;
        width: fit-content;
        box-shadow: 0 0 10px #cac6c6;
        display: inline-block;
    }
</style>