<template>
    <el-container class="set-horizonal" style="height: 80vh;margin-top: 5vh;padding-left: 2.5vw;">
        <add-announcement :inputRows="20" style="width: 50%;margin-right: 5vw;margin-left: 2.5vw;"/>
        <past-announcement-vue style="width: 50%;margin-right: 5vw;margin-left: 2.5vw;"/>
    </el-container>
</template>

<script>
import AddAnnouncement from '@/components/AdminComponents/AddAnnouncement.vue';
import PastAnnouncementVue from '@/components/AdminComponents/PastAnnouncement.vue';
export default{
    components:{
        AddAnnouncement,
        PastAnnouncementVue,
    },
    data(){
        
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