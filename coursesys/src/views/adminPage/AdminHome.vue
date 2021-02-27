<template>
    <div style="height: 100%">
        <el-container>
            <el-header>
                <div class="homeTitle">课程管理系统</div>
                <div class="dropdownMenu">
                    <el-dropdown size="medium" :hide-on-click="false" placement="bottom" @command="handleCommand">
                        <span class="el-dropdown-link">
                            {{studentId}}
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu style="border-right: 0px">
                        <el-menu-item index="1-1" @click="courseManage">课程管理</el-menu-item>
                        <el-menu-item index="1-2" @click="studentManage">学生管理</el-menu-item>
                        <el-menu-item index="1-3" @click="gradeTable">成绩分布</el-menu-item>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        data() {
            return {
               studentId: 0,
            };
        },
        methods: {
            courseManage() {
                this.$router.push('/courseManage')
            },
            studentManage() {
                this.$router.push('/studentManage')
            },
            gradeTable() {
                this.$router.push('/gradeTable')
            },
            handleCommand(command) {
                switch (command) {
                    case "logout":
                        window.sessionStorage.removeItem("studentId")
                        this.$router.push("/")
                        this.$message("已退出登录")
                        break;
                
                    default:
                        break;
                }
            }
        },
        beforeMount() {
            if (!window.sessionStorage.getItem('studentId') || window.sessionStorage.getItem('studentId') != 'admin') {
                    this.$message.error('请登录管理员帐号')
                    this.$router.replace("/")
                    return null
            }
            this.studentId = window.sessionStorage.getItem("studentId");
        },
        return: "AdminHome"
    }
</script>

<style>
    .el-header {
        background-color: #95b6e2;
        color: white;
        align-items: center;
        font-family: 微软雅黑;
        font-size: 20px
    }

    .el-aside {
        /* background-color: #D3DCE6; */
        color: #333;
        text-align: center;
        line-height: 200px;
        border-right: solid 1px rgb(214, 214, 214);
        height: 100%;
    }

    .el-main {
        /* background-color: #E9EEF3; */
        color: #333;
        text-align: left;
        line-height: 40px;
    }

    body>.el-container {
        margin-bottom: 40px;
    }

    .el-container {
        height: 100%;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: white;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }

    .homeTitle {
        display: inline-block;
        margin: 20px 20px 20px 20px
    }

    .dropdownMenu {
        float: right;
        padding: 15px 0 15px 0;
        display: block;

    }


</style>