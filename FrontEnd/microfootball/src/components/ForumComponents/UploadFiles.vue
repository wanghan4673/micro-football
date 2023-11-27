<template>
    <input type="file" ref="clearFile" @change="getFile($event)" multiple="multiplt" class="add-file-right-input"
        style="margin-left:10px;" accept=".png,.jpg,.jpeg">
    <div class="filebox" v-for="file in fileList" :key="file" style="gap:10px;height: 20px;">
        <span>{{ file.name }}</span>
        <el-icon style="padding-top: 15px;" @click="removefile(file)">
            <CircleCloseFilled />
        </el-icon>
    </div>
</template>


<script setup>
import { ref, defineProps } from 'vue'
import { useGeneralStore } from '@/stores/general';
const store = useGeneralStore()

import { ElMessage, ElMessageBox } from 'element-plus'

const fileList = ref([])
const getFile = (event) => {
    var file = event.target.files;
    for (var i = 0; i < file.length; i++) {
        //上传类型判断
        var fileName = file[i].name;
        var idx = fileName.lastIndexOf(".");
        if (idx != -1) {
            var ext = fileName.substr(idx + 1).toUpperCase();
            ext = ext.toLowerCase();
            if (ext == 'png' || ext == 'jpg' || ext == 'jpeg') {
                store.drafts.img.push(file[i])
                fileList.value.push(file[i])
            }
        } else {
            ElMessage({
                message: '添加文件失败',
                type: 'error',
            })
        }
    }
}
const removefile = (file) => {
    let index = fileList.value.indexOf(file);
    if (index > -1) {
        fileList.value.splice(index, 1);
    }
    index = store.drafts.img.indexOf(file);
    if (index > -1)
        store.drafts.img.splice(index, 1);
}


</script>
<style scoped></style>