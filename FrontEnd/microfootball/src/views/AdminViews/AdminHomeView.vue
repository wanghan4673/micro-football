<script>
import NewUsersChart from '@/components/AdminComponents/NewUsersChart.vue';
import NewReportedPosts from '@/components/AdminComponents/NewReportedPosts.vue';
import AddAnnouncement from '@/components/AdminComponents/AddAnnouncement.vue';
import WorkBench from '@/components/AdminComponents/WorkBench.vue';
export default{
    components:{
        NewUsersChart,
        NewReportedPosts,
        AddAnnouncement,
        WorkBench,
    },
    data(){
        return{
            
        }
    },
    methods:{
        directToSubsystem(mode) {
            if (mode == 0) {
                this.$router.push('/adminHome');
            } else if (mode == 1) {
                this.$router.push('/userManage');
            } else if (mode == 2) {
                this.$router.push('/forumManage');
            } else if (mode == 3) {
                this.$router.push('/newsManage');
            } else if (mode == 4) {
                this.$router.push('/announceManage');
            }
        },
    }
}
</script>


<template>
    <el-row style="height: 10vh;">
        <el-col :span="24"><h1 class="welcome-text" >欢迎回来，管理员</h1></el-col>
    </el-row>
    <el-row style="height: 75vh;">
        <el-col :span="8" class="set-vertical" style="margin-top: 2vh;">
            <new-users-chart style="margin-left: 5vw;height: 35vh;"/>
            <el-card style="margin-left: 5vw;margin-top: 2vh;height: 35vh;">
                <h3>
                    饼图
                </h3>
            </el-card>
        </el-col>
        <el-col :span="8" style="margin-top: 2vh;">
            <el-card style="margin-left: 1vw;margin-right: 1vw;height: 72vh;">
                <h3>
                    新增举报
                </h3>
                <new-reported-posts style="height: 60vh;margin-top: 2vh;"/>
            </el-card>
        </el-col>
        <el-col :span="8" style="margin-top: 2vh;">
            <work-bench style="height: 25vh;"/>
            <add-announcement :inputRows="8" style="margin-top: 2vh;height: 45vh;"/>
        </el-col>
    </el-row>
</template>

<style scoped>
.welcome-text {
    margin-left: 42vw;
    height: 10vh;
    margin-top: 3vh;
    font-size: 2rem;
    position: absolute;
    opacity: 0;
    animation: flyIn 1s ease forwards;
}

@keyframes flyIn {
  0% {
    transform: translateY(100%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>