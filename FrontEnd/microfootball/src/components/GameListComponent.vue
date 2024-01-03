<template>
    <!-- 列表 -->
    <a-list
      class="demo-loadmore-list"
      :loading="initLoading"
      item-layout="horizontal"
      :data-source="list"
    >
      <template #loadMore>
        <div
          v-if="!initLoading && !loading"
          :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }"
        >
          <a-button @click="onLoadMore">loading more</a-button>
        </div>
      </template>
      <template #renderItem="{ item }">
        <a-list-item>
          <template #actions>
            <!-- <a key="list-loadmore-more" @click="() => showMoreInfo(item)">more</a> -->
            <router-link :to="{ name: 'GameInfo', params: { id: item.id } }">more</router-link>
          </template>
          <a-skeleton avatar :title="false" :loading="!!item.loading" active>
            <a-list-item-meta
            description="">
              <template #title>
                <a>{{ item.homeTeamName }} vs. {{ item.awayTeamName }}</a>
              </template>
              <template #avatar>
                <a-avatar :src="item.homeTeamLogo" />
              </template>
            </a-list-item-meta>
            <div>{{ item.venueCity }} . {{ item.venueCity }} . {{ item.status }}</div>
          </a-skeleton>
        </a-list-item>
      </template>
    </a-list>


</template>
<script setup>
import { onMounted, ref, nextTick, watch } from 'vue';

// props 接收 dataList
const props = defineProps(['dataList']);

// 初始化变量
const initLoading = ref(true);
const loading = ref(false);
const data = ref([]);
const list = ref([]);
const index = ref(3);
const open = ref(false)

// 在组件挂载时初始化数据
onMounted(() => {
  initLoading.value = false;
  data.value = [...props.dataList]; // 使用扩展运算符复制数组，确保 data 与传入的 dataList 独立
  list.value = data.value.slice(0, 3);
  console.log('listcomponent', data.value)
  watch(() => props.dataList, (newDataList) => {
    data.value = [...newDataList];
    list.value = data.value.slice(0, 3);
    console.log('listcomponent')
  });
});

// 加载更多数据的函数
const onLoadMore = () => {
  loading.value = true;
  console.log('listcomponent', data.value)
  for (let i = 0; index.value < data.value.length && i < 3; i++, index.value++) {
    list.value.push(data.value[index.value]);
  }
  loading.value = false;
  nextTick(() => {
    window.dispatchEvent(new Event('resize'));
  });
};
</script>

<style scoped>
  .demo-loadmore-list {
    min-height: 450px;
  }
</style>