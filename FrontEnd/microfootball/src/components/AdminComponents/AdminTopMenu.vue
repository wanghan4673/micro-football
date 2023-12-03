<template>
    <div class="topMenu">
        <el-container class="nav-logo">
            <img src="@/assets/img/tjlogo.png" style="height: 7vh;margin-top: 0.5vh;"/>
            <h3 style="margin-left: 1vw;margin-top: 2vh;">管理员系统</h3>
        </el-container>
        <el-container style="width: 40vw;margin-left: 0;margin-top: 0.5vh;">
            <el-menu :default-active="adminHome" mode="horizontal" active-text-color="#ffd04b" 
                @select="handleMenuSelect" style="border: 0 !important;width: 40vw;">
                <el-menu-item index="adminHome" @click="directToSubsystem(0)">系统总览</el-menu-item>
                <el-menu-item index="userManage" @click="directToSubsystem(1)">用户管理</el-menu-item>
                <el-menu-item index="forumManage" @click="directToSubsystem(2)">论坛管理</el-menu-item>
                <el-menu-item index="newsManage" @click="directToSubsystem(3)">新闻管理</el-menu-item>
                <el-menu-item index="announceManage" @click="directToSubsystem(4)">公告管理</el-menu-item>
            </el-menu>
        </el-container>
        <el-container style="width: 30vw;">
            <h4 style="margin-left: 20vw;">退出管理员系统</h4>
        </el-container>
    </div>
</template>

<script>
export default{
    data(){
        return{

        }
    },
    methods:{
        directToSubsystem(mode){
            if(mode==0){
                this.$router.push('/adminHome');
            }else if(mode==1){
                this.$router.push('/userManage');
            }else if(mode==2){
                this.$router.push('/forumManage');
            }else if(mode==3){
                this.$router.push('/newsManage');
            }else if(mode==4){
                this.$router.push('/announceManage');
            }
        }
    }
}
</script>

<style scoped>
.nav-logo{
    margin-left: 1vw;
    width: 10vw;
}
.topMenu{
    width: 100%;
    height: 8vh;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid;
    padding-bottom: 1vh;
}
.website-title {
    font-weight: bold;
    font-size: 1.2rem;
    display: flex;
    align-items: center;
}
.font-set{
    color: #000000;
}
</style>
