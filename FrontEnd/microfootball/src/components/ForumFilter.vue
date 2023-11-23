<template>
    <div style="display: flex; justify-content: space-between; flex-wrap: wrap; width: 90%;">
        <div style="display: flex;">
            <el-input style=" max-width: 300px;" v-model="forumstore.keyword" placeholder="关键词">
                <template #suffix>
                    <el-icon class="el-input__icon">
                        <Search />
                    </el-icon>
                </template>
            </el-input>
        </div>
        <div class="sort-container">
            <el-button :class="{ 'sort-button': forumstore.sorttype == 'like' }"
                @click="changeSort('like')">按热度排序</el-button>
            <el-button :class="{ 'sort-button': forumstore.sorttype == 'time' }"
                @click="changeSort('time')">按时间排序</el-button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useForumStore } from '../stores/forum.ts'
const forumstore = useForumStore()

const changeSort = (type) => {
    if(type == 'like')
    {
        forumstore.sorttype = 'like'
    }else{
        forumstore.sorttype = 'time'
    }
}
</script>

<style lang="scss" scoped>
.sort-container {
    /* 排序按钮 */
}
.sort-button {
    /* 排序按钮 */
    color: orange;
}
</style>