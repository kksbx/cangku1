<template>
  <div class="diary-detail-container">
    <!-- 左侧部分 -->
    <div class="left-section">
      <!-- 日记名称 -->
      <div class="diary-title">
        <h3>日记名称</h3>
        <span>{{ diaryData.title }}</span>
      </div>
      <!-- 作者 -->
      <div class="diary-author">
        <h3>作者</h3>
        <span>{{ diaryData.author }}</span>
      </div>
      <!-- 目的地 -->
      <div class="diary-destination" @click="handleDestinationClick">
        <h3>目的地</h3>
        <span>{{ diaryData.destination }}</span>
      </div>
      <!-- 旅行图片 -->
      <div class="diary-images">
        <h3>旅行图片</h3>
        <img v-for="(image, index) in diaryData.images" :key="index" :src="image" alt="diary image">
      </div>
    </div>
    <!-- 右侧部分 -->
    <div class="right-section">
      <!-- 日记内容 -->
      <div class="diary-content">
        <h3>日记内容</h3>
        <p>{{ diaryData.content }}</p>
      </div>
      <!-- 显示热度 -->
      <div class="diary-hot">
        <h3>热度: {{ diaryData.hot }}</h3>
      </div>
      <!-- 评分板块 -->
      <div class="diary-rating">
        <h3>评分</h3>
        <div class="star-rating">
          <span
            v-for="(star, index) in 5"
            :key="index"
            :class="{ 'star-filled': hasRated ? index < submittedRating : index < hoverRating }"
            @mouseenter="handleMouseEnter(index + 1)"
            @mouseleave="handleMouseLeave"
            @click="submitRating(index + 1)"
            :style="{ cursor: hasRated ? 'default' : 'pointer' }"
          >★</span>
        </div>
        <div v-if="hasRated">
          <p>您已评分为: {{ submittedRating }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

// 定义 DiaryData 类型
interface DiaryData {
  id: number;
  title: string;
  author: string;
  images: string[];
  content: string;
  destination: string;
  hot: number;
  rating: number;
}

// // 模拟从后端获取日记数据的函数，实际使用时需替换为真实的 API 调用
// const fetchDiaryData = async (diaryId: number): Promise<DiaryData> => {
//   const response = await axios.get(`http://localhost:8050/getdiary/${diaryId}`);
//   return {
//     ...response.data,
//     hot: response.data.hot || 0,
//     rating: response.data.rating || 0
//   };
// };
const fetchDiaryData = async (diaryId: number) => {
  try {
    const response = await axios.get(`/get/diary/${diaryId}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('获取日记详情失败:', error);
    return null;
  }
};
const router = useRouter();
const route = useRoute();
// 使用具体的 DiaryData 类型
const diaryData = ref<DiaryData>({
  id: 0,
  title: '',
  author: '',
  images: [],
  content: '',
  destination: '',
  hot: 0,
  rating: 0
});

const hoverRating = ref(0);
const submittedRating = ref(0);
const hasRated = ref(false);

onMounted(async () => {
  const diaryId = Number(route.params.id);
  diaryData.value = await fetchDiaryData(diaryId);
  // 进入页面后增加热度
  await increaseHot();
  // 检查用户是否已经评分
  await checkIfRated(diaryId);
});

// 处理目的地点击事件
const handleDestinationClick = () => {
  router.push('#');
  axios.get("http://localhost:8050/test1").then(response => {
    console.log(response.data);
  });
  axios.post(`http://localhost:8050/sign_up_${"wan"}${"wan"}`).then(response => {
    console.log(response.data);
  });
};

// 增加热度
const increaseHot = async () => {
  const diaryId = Number(route.params.id);
  try {
    //await axios.post(`http://localhost:8050/diary/${diaryId}/add_hot`);
    diaryData.value.hot++;
  } catch (error) {
    console.error('增加热度失败:', error);
  }
};

// 检查用户是否已经评分
const checkIfRated = async (diaryId: number) => {
  try {
    //const response = await axios.get(`http://localhost:8050/diary/${diaryId}/check-rated`);
    //hasRated.value = response.data.hasRated;
    // if (hasRated.value) {
    //   submittedRating.value = response.data.rating;
    // }
    hasRated.value = false;
  } catch (error) {
    console.error('检查评分状态失败:', error);
  }
};

// 鼠标悬停处理
const handleMouseEnter = (rating: number) => {
  if (!hasRated.value) {
    hoverRating.value = rating;
  }
};

// 鼠标离开处理
const handleMouseLeave = () => {
  if (!hasRated.value) {
    hoverRating.value = 0;
  }
};

// 提交评分
const submitRating = async (rating: number) => {
  const diaryId = Number(route.params.id);
  if (!hasRated.value) {
    try {
      //await axios.post(`http://localhost:8050/diary/${diaryId}/rating`, { rating });
      diaryData.value.rating = rating;
      submittedRating.value = rating;
      hasRated.value = true;
    } catch (error) {
      console.error('提交评分失败:', error);
    }
  }
};
</script>

<style scoped>
.diary-detail-container {
  display: flex;
  padding: 30px;
  margin-top: 70px;
  gap: 30px;
  margin-left: 120px; /* 新增，根据侧边栏宽度调整，这里假设侧边栏宽120px */
}

.left-section {
  width: 40%;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.right-section {
  width: 60%;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.diary-title h3,
.diary-author h3,
.diary-destination h3,
.diary-images h3,
.diary-content h3,
.diary-hot h3,
.diary-rating h3 {
  margin-bottom: 5px;
  color: #333;
  font-size: 1.1em;
}

.diary-title span,
.diary-author span,
.diary-destination span {
  color: #666;
  display: block;
  margin-bottom: 15px;
}

.diary-destination {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.diary-destination:hover {
  background-color: #f0f5ff;
}

.diary-images img {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
  border-radius: 5px;
}

.diary-content p {
  line-height: 1.6;
  color: #666;
}

.star-rating {
  font-size: 24px;
  color: #ccc;
}

.star-filled {
  color: gold;
}
</style>
