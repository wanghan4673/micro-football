<!-- 个人名片 -->
<template>
    <div class="user-card-box">
        <div class="user-avator-box">
            <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" class="user-avator"
                alt="Avatar"></el-avatar>
        </div>
        <div class="user-center-box">
            <div class="username-box">
                {{ name }}
            </div>
            <div class="signature-box">
                <p class="signature-detail">
                    {{ signature }}
                </p>
            </div>
        </div>
        <div class="user-right-box">
            <div class="number-detail" style="cursor: pointer;" @click="toMyFollow()">
                关注数: {{ followNum }}
            </div>
            <div class="number-detail" style="cursor: pointer;" @click="toMyFans()">
                粉丝数: {{ fansNum }}
            </div>
            <div class="number-detail">
                积分总量: {{ score }}
            </div>
            <div class="change-league-button" @click="toChangeLeague()">
                选择关注赛事
            </div>
            <div class="edit-profile-button" @click="toEditProfile()">
                修改个人资料
            </div>
            <div class="change-password-button" @click="toChangePassword()">
                修改密码
            </div>
        </div>
        <el-dialog title="修改个人资料" v-model="editDialogVisible" width="30%" class="edit-user-box">
            <el-form :model="userCardForm" :rules="rules" label-width="80px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="userCardForm.username"></el-input>
                </el-form-item>
                <el-form-item label="个性签名" prop="usersignature">
                    <el-input v-model="userCardForm.usersignature"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="useremail">
                    <el-input v-model="userCardForm.useremail"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitEditForm">提交</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改密码" v-model="changePwVisible" width="30%" class="edit-user-box">
            <el-form :model="twoPwForm" :rules="pwRules" label-width="80px">
                <el-form-item label="原密码" prop="originpassword">
                    <el-input v-model="twoPwForm.originpassword"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newpassword">
                    <el-input v-model="twoPwForm.newpassword"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changePwVisible = false">取消</el-button>
                <el-button type="primary" @click="submitNewPassword">提交</el-button>
            </div>
        </el-dialog>
        <el-dialog title="选择关注赛事" v-model="changeLgVisible" class="edit-league-box">
            <el-radio-group v-model="selectedLeague">
                <el-radio label="英超">英超</el-radio>
                <el-radio label="西甲">西甲</el-radio>
                <el-radio label="意甲">意甲</el-radio>
                <el-radio label="德甲">德甲</el-radio>
                <el-radio label="法甲">法甲</el-radio>
                <el-radio label="中超">中超</el-radio>
                <el-radio label="同济">同济</el-radio>
            </el-radio-group>
            <div slot="footer" class="dialog-footer">
                <el-button @click="changeLgVisible = false">取消</el-button>
                <el-button type="primary" @click="submitSelectedLeague">提交</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineEmits, reactive } from 'vue'
import { ElMessage, type FormRules } from 'element-plus'
import axios from 'axios'
import { sha256 } from 'js-sha256';

interface RuleForm {
    username: string
    usersignature: string
    useremail: string
}
interface pwRuleForm {
    originpassword: string
    newpassword: string
}

const name = ref<string>('')
const signature = ref<string>('')
const score = ref<number>(0)
const followNum = ref<number>(0)
const fansNum = ref<number>(0)
const edmitEvents = defineEmits(['son-click'])  // 向父组件传值
const isPost = ref(true)
const isFans = ref(false)
const isFollow = ref(false)
const editDialogVisible = ref(false)
const changePwVisible = ref(false)
const changeLgVisible = ref(false)
const selectedLeague = ref<string>('')
const userCardForm = ref({
    username: '',
    usersignature: '',
    useremail: '',
})
const twoPwForm = ref({
    originpassword: '',
    newpassword: '',
})

const rules = reactive<FormRules<RuleForm>>({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { max: 10, message: '用户名不可超过10个字符', trigger: ['blur', 'change'] }
    ],
    usersignature: [
    ],
    useremail: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
    ]
})

const pwRules = reactive<FormRules<pwRuleForm>>({
    originpassword: [

    ],
    newpassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { pattern: /^[a-zA-Z0-9]+$/, message: '密码只能由数字和字母组成', trigger: 'blur' },
        { max: 12, message: '密码不可超过12位', trigger: 'blur' }
    ]
})

onMounted(() => {
    getUserInfo()
})

const getUserInfo = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/users/user-info', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            name.value = response.data.data.name
            signature.value = response.data.data.signature
            score.value = response.data.data.score
            followNum.value = response.data.data.follow
            fansNum.value = response.data.data.fans
            console.log(response.data.data)
        } else {
            ElMessage({
                message: '获取积分失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '获取积分请求发送失败',
            type: 'error',
        })
    }
}

const toMyFollow = () => {
    isFans.value = false
    isFollow.value = true
    isPost.value = false
    edmitEvents('son-click', isPost.value, isFans.value, isFollow.value)
}
const toMyFans = () => {
    isFans.value = true
    isFollow.value = false
    isPost.value = false
    edmitEvents('son-click', isPost.value, isFans.value, isFollow.value)
}
const toEditProfile = () => {
    editDialogVisible.value = true;
}

const toChangePassword = () => {
    changePwVisible.value = true;
}

const toChangeLeague = () => {
    changeLgVisible.value = true;
}

const submitEditForm = async () => {
    const { username, usersignature, useremail } = userCardForm.value
    let formData = new FormData()
    formData.append("name", username)
    formData.append("signature", usersignature)
    formData.append("email", useremail)
    const token = localStorage.getItem('token')
    try {
        const response = await axios.put('/api/users', formData, {
            headers: {
                'token': token,
            }
        })
        if (response.data.code === 1) {
            ElMessage({
                message: '个人信息更新成功',
                type: 'success',
            })
            setTimeout(() => {
                window.location.reload()
            }, 500)
        } else {
            ElMessage({
                message: '个人信息更新失败',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '修改个人信息请求发送失败',
            type: 'error',
        })
    }
    editDialogVisible.value = false
};

const submitNewPassword = async () => {
    const { originpassword, newpassword } = twoPwForm.value
    const token = localStorage.getItem('token')
    let response
    try {
        response = await axios.put('/api/users/password', {
            oriPassword: sha256(originpassword),
            newPassword: sha256(newpassword)
        }, {
            headers: {
                'token': token,
            }
        })
        if (response.data.code === 1) {
            ElMessage({
                message: '密码修改成功',
                type: 'success',
            })
        } else {
            ElMessage({
                message: '密码修改失败,原密码错误!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '修改密码请求发送失败',
            type: 'error',
        })
    }
    changePwVisible.value = false
}

const submitSelectedLeague = async () => {
    const token = localStorage.getItem('token')
    let formData = new FormData()
    formData.append("league",selectedLeague.value)
    let response
    try {
        response = await axios.put('/api/users/favorite-league', formData, {
            headers: {
                'token': token,
            }
        })
        console.log(response.data)
        if (response.data.code === 1) {
            ElMessage({
                message: '关注联赛成功',
                type: 'success',
            })
        } else {
            ElMessage({
                message: '关注联赛失败!',
                type: 'error',
            })
        }
    } catch (error) {
        ElMessage({
            message: '更新关注联赛请求发送失败',
            type: 'error',
        })
    }
    changeLgVisible.value = false
}
</script>

<style lang="scss">
@import '../../../assets/css/frame.scss';

.user-card-box {
    // 名片
    @extend .grid-three-container;
    height: 20vh;
    background-color: #c4e7fe;
    border-radius: 1rem;
}

.user-avator-box {
    // 头像居中
    display: flex;
    justify-content: center;
    align-items: center;
}

.user-avator {
    // 头像大小
    width: 6rem;
    height: 6rem;
}

.username-box {
    // 用户名部分
    height: 2rem;
    font-size: 2rem;
    margin-bottom: 1rem;
}

.signature-box {
    // 签名部分
    height: 5rem;
    font-size: 1rem;

    .signature-detail {
        // 只展示两行
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
}

.user-right-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
}

.number-detail {
    margin-bottom: 0.2rem;
    font-size: 0.8rem;
}

.edit-profile-button,
.change-password-button,
.change-league-button {
    color: blue;
    font-size: 0.8rem;
    margin-top: 0.5rem;
    width: 6rem;
    transition: transform 0.3s ease;

    &:hover {
        color: red;
        transform: scale(1.1);
        cursor: pointer;
    }
}

.edit-user-box {
    position: absolute;
    display: flex;
    flex-direction: column;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -75%);
    width: 35vw;
}

.edit-league-box {
    position: absolute;
    display: flex;
    flex-direction: column;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -75%);
    width: 45vw;
}

.dialog-footer {
    margin-top: 2vh;
    display: flex;
    justify-content: flex-end;
}
</style>