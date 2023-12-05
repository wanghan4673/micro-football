<template>
    <el-card>
        <el-container style="margin-left: 2.5vw;">
            <h3>公告标题</h3>
            <el-input style="width: 50%;margin-left: 2vw;" placeholder="公告标题"/>
        </el-container>
        <el-input
            v-model="announcement"
            :rows="inputRows"
            type="textarea"
            placeholder="请输入公告"
            style="margin-top: 2vh;"
        />
        <el-container class="set-horizonal" style="margin-top: 2vh;margin-left: 10%;">
            <el-button type="primary" round style="width: 40%;">发布</el-button>
            <el-button type="primary" round style="width: 40%; margin-left: 3vw;">清空</el-button>
        </el-container>
    </el-card>
</template>

<script>
export default {
    name:'AddAnnouncement',
    props:{
        inputRows:String,
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