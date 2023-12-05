<template>
    <el-container class="set-horizonal" style="height: 80vh;margin-top: 5vh;padding-left: 2.5vw;">
        <add-announcement :inputRows="20" style="width: 50%;margin-right: 5vw;margin-left: 2.5vw;"/>
        <el-card style="width: 50%;margin-right: 5vw;margin-left: 2.5vw;">
            <h3>往期公告列表</h3>

        </el-card>
    </el-container>
</template>

<script>
import WangEditer from '@/components/ForumComponents/WangEditer.vue';
import AddAnnouncement from '@/components/AdminComponents/AddAnnouncement.vue';
export default{
    components:{
        WangEditer,
        AddAnnouncement,
    },
    data(){
        return{

        }
    },
    methods:{

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