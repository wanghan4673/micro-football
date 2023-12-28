<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-switch
                v-model="viewUserBanned"
                style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
                active-text="被封禁用户列表"
                inactive-text="用户列表"
            />
            <el-card v-if="viewUserBanned == true" style="height: 74vh;margin-top: 2vh;">
                <h3 style="margin-top: 2vh;">被封禁用户列表</h3>
                <el-table v-if="reportedUsers != []" :data="reportedUsers" height="65vh" style="width: 100%;border-radius: 10px;">
                    <el-table-column align="center" prop="id" label="用户Id" width="100" />
                    <el-table-column prop="name" label="用户昵称" width="150" />
                    <el-table-column align="center" prop="createDate" label="注册时间" width="150" />
                    <el-table-column align="center" prop="score" label="积分" width="100" />
                    <el-table-column align="center" prop="fans" label="粉丝数" width="100" />
                    <el-table-column fixed="right" label="操作">
                        <template #default="scope">
                            <el-button v-if="this.reportedUsers[scope.$index].isbanned == true" link type="primary"
                                size="small" @click="updateReportedUser(scope.$index)">解封用户</el-button>
                            <el-button v-if="this.reportedUsers[scope.$index].isbanned == false" disabled link type="primary"
                                size="small">已解封用户</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
            <el-card v-if="viewUserBanned == false" style="height: 74vh;margin-top: 2vh;">
                <h3 style="margin-top: 2vh;">用户列表</h3>
                <el-table v-if="allUsers!=[]" :data="allUsers" height="65vh" style="width: 100%;border-radius: 10px;">
                    <el-table-column align="center" prop="id" label="用户Id" width="100" />
                    <el-table-column prop="name" label="用户昵称" width="150" />
                    <el-table-column align="center" prop="createDate" label="注册时间" width="150" />
                    <el-table-column align="center" prop="score" label="积分" width="100" />
                    <el-table-column align="center" prop="fans" label="粉丝数" width="100" />
                    <el-table-column fixed="right" label="操作">
                        <template #default="scope">
                            <el-button v-if="this.allUsers[scope.$index].isbanned == false" link type="primary"
                                size="small" @click="killUser(scope.$index)">封禁用户</el-button>
                            <el-button v-if="this.allUsers[scope.$index].isbanned == true" disabled link type="primary"
                                size="small">已封禁用户</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 2vw;">
            <new-users-chart style="height: 50vh;margin-top: 6vh;"/>
            <el-card style="margin-top: 2vh;height: 32vh;">
                <h3>可疑用户</h3>
            </el-card>
        </el-container>
    </el-container>
</template>

<script>
import NewUsersChart from '@/components/AdminComponents/NewUsersChart.vue';
import axios from "axios"
import { ElMessage } from 'element-plus';
export default{
    components:{
        NewUsersChart,
    },
    data(){
        return{
            viewUserBanned:false,
            allUsers:[],
            reportedUsers:[],
        }
    },
    methods:{
        async init(){
            this.getAllUsers();
            this.getReportedUser();
        },
        async getAllUsers() {
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/user/getAllUser', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.allUsers=response.data.data
                }
            } catch (e) {
                console.log(e)
            }
        },
        async getReportedUser(){
            const adminToken = localStorage.getItem('adminToken');
            try {
                const response = await axios.get('/api/admin/user/reportedUser', {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.reportedUsers = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
        },
        async updateReportedUser(index){
            const adminToken = localStorage.getItem('adminToken');
            const formData = new FormData()
            formData.append('id', this.reportedUsers[index].id);
            try {
                const response = await axios.post('/api/admin/user/updateBanStatus', formData,{
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    ElMessage.success("解封成功")
                    this.reportedUsers.splice(index,1);
                }
            } catch (e) {
                console.log(e)
            }
        },
        async killUser(index) {
            const adminToken = localStorage.getItem('adminToken');
            const formData = new FormData()
            formData.append('id', this.allUsers[index].id);
            try {
                const response = await axios.post('/api/admin/user/updateBanStatus',formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                if (response.status == 200) {
                    ElMessage.success("封禁成功")
                    this.allUsers[index].isbanned = true
                }
            } catch (e) {
                console.log(e)
            }
        }
    },
    mounted(){
        this.init()
    },
}
</script>

<style scoped>
.set-vertical{
    display: flex;
    flex-direction: column;
}
.set-horizonal{
    display: flex;
    flex-direction: row;
}
</style>