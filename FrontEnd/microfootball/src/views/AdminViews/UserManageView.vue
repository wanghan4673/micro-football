<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-switch
                v-model="viewUserBanned"
                style="--el-switch-on-color: #ff4949; --el-switch-off-color: #13ce66"
                active-text="被封禁帖子列表"
                inactive-text="论坛帖子列表"
            />
            <el-card v-if="viewUserBanned == true" style="height: 74vh;margin-top: 2vh;">
                <h3 style="margin-top: 2vh;">被封禁用户列表</h3>
            </el-card>
            <el-card v-if="viewUserBanned == false" style="height: 74vh;margin-top: 2vh;">
                <h3 style="margin-top: 2vh;">正常用户列表</h3>
            </el-card>
        </el-container>
        <el-container class="set-vertical" style="margin-right: 5vw;width: 30vw;margin-left: 2vw;">
            <new-users-chart style="height: 40vh;"/>
            <el-card style="margin-top: 2vh;height: 40vh;">
                <h3>可疑用户</h3>
            </el-card>
        </el-container>
    </el-container>
</template>

<script>
import NewUsersChart from '@/components/AdminComponents/NewUsersChart.vue';
export default{
    components:{
        NewUsersChart,
    },
    data(){
        return{
            viewUserBanned:false,
        }
    },
    methods:{

    }
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