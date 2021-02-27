<template>
    <div>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" class="loginBox">
            <h3 class="loginTitle">登录</h3>
            <el-form-item label="用户名" prop="username">
                <el-input v-model="loginForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="loginForm.password"></el-input>
            </el-form-item>
            <div class="loginTitle">
                <el-button type="primary" @click="submitLogin('loginForm')" class="loginButton">登录</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                loginForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [{
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    }, ],
                    password: [{
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    }, ]
                }
            };
        },
        methods: {
            submitLogin(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.postRequest("/login", this.loginForm).then(resp => {
                            if (resp && resp.code == 200) {
                                
                                this.$message.success("登录成功")
                                if (resp.obj.studentId != 0) {
                                    window.sessionStorage.setItem('studentId', resp.obj.studentId)
                                    this.$router.replace('/studentHome')
                                }
                                else {
                                    window.sessionStorage.setItem('studentId', 'admin')
                                    this.$router.replace('/adminHome')
                                }
                            }
                        })
                    } else {
                        this.$message.error("请输入所有字段")
                        return false;
                    }
                });
            },
        },
        return: "Login"
    }
</script>

<style>
    .loginBox {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 10px #cac6c6;
        align-content: center;
    }

    .loginTitle {
        margin: 0px auto 20px auto;
        text-align: center;
    }

    .loginButton {
        display: inline-block;
    }
</style>