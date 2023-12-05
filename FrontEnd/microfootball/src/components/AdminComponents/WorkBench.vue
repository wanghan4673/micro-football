<template>
    <el-card>
        <h3 style="height: 5vh;margin-top: 0;">
            工作台
        </h3>
        <el-container class="set-horizonal" style="height: 20vh;padding-top: 3vh;padding-left: 3vw;">
            <el-container class="set-vertical">
                <el-icon size="3em" color="red" class="workboard" @click="directToSubsystem(1)"><User /></el-icon>
                <h5 @click="directToSubsystem(1)" class="workboard">用户管理</h5>
            </el-container>
            <el-container class="set-vertical">
                <el-icon size="3em" color="blue" class="workboard" @click="directToSubsystem(2)"><ChatLineRound /></el-icon>
                <h5  @click="directToSubsystem(2)" class="workboard">论坛管理</h5>
            </el-container>
            <el-container class="set-vertical">
                <el-icon size="3em" color="green" class="workboard" @click="directToSubsystem(3)"><Timer /></el-icon>
                <h5  @click="directToSubsystem(3)" class="workboard">新闻管理</h5>
            </el-container>
            <el-container class="set-vertical">
                <el-icon size="3em" color="brown" class="workboard" @click="directToSubsystem(4)"><Monitor /></el-icon>
                <h5  @click="directToSubsystem(4)" class="workboard">公告管理</h5>
            </el-container>
        </el-container>
    </el-card>
</template>

<script>
export default {
    data() {
        return {

        }
    },
    methods: {
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
        }
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
.workboard{
    cursor: pointer;
}
</style>