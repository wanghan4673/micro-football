<template>
    <el-container style="height: 80vh;margin-top: 5vh;" class="set-horizonal">
        <el-container style="margin-left: 5vw;width: 55vw;" class="set-vertical">
            <el-card style="height: 40vh;">
                <el-input v-model="keyword" placeholder="根据关键词搜索用户" clearable style="width: 20vw;">
                    <template #prefix>
                    <el-icon class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <el-button type="primary" style="margin-left: 2vw;">搜索</el-button>
                <h3 style="margin-top: 2vh;">被封禁用户列表</h3>
            </el-card>
            <el-card style="height: 40vh;margin-top: 2vh;">
                <el-input v-model="keyword" placeholder="根据关键词搜索用户" clearable style="width: 20vw;">
                    <template #prefix>
                    <el-icon class="el-input__icon"><search /></el-icon>
                    </template>
                </el-input>
                <el-button type="primary" style="margin-left: 2vw;">搜索</el-button>
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