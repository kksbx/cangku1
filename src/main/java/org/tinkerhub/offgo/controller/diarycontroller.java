package org.tinkerhub.offgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinkerhub.offgo.entity.ContentEntity;
import org.tinkerhub.offgo.entity.ImageEntity;
import org.tinkerhub.offgo.entity.diary;
import org.tinkerhub.offgo.mysql_service.Diary_service;
import org.tinkerhub.offgo.mysql_service.User_service;

import java.io.*;
import java.util.Random;

@RestController
public class diarycontroller {
    @Autowired
    private Diary_service diaryService;
    @Autowired
    private User_service userService;
    @RequestMapping("/clean_database")
    public boolean cleanDatabase() {
        diaryService.cleanDiaryData();
        userService.deleteall();
        return true;
    }
    private static final String IMAGE_STORAGE_DIR = "src/main/resources/static/images/diary/";

    @RequestMapping("/savediary_withoutimage")
    public boolean savediary_without(@RequestParam String title, @RequestParam String description, @RequestParam String content,@RequestParam int userID) {
        int contentID = diaryService.find_content_max_id() + 1;
        ContentEntity contentEntity = new ContentEntity(contentID, content);
        diaryService.saveContent(contentEntity);
        int diaryID = diaryService.find_image_max_id() + 1;
        diaryService.saveDiary(new diary(diaryID,title, userID, description, contentID));
        return true;
    }
    @RequestMapping("/savediary_withimage")
    public boolean savediary_with(@RequestParam String title, @RequestParam String description, @RequestParam String content, @RequestParam int userID,  @RequestParam int image_num) {
        int contentID = diaryService.find_content_max_id() + 1;
        ContentEntity contentEntity = new ContentEntity(contentID, content);
        diaryService.saveContent(contentEntity);
        int diaryID = diaryService.find_image_max_id() + 1;
        int imageID = diaryService.find_image_max_id() + 1;
        int[]arr=new int[image_num];
        imageID-=image_num;
        for (int i = 0; i < image_num; i++) {
            arr[i]=imageID;
            imageID++;
        }
        diary diary = new diary(diaryID, title, userID, description, contentID,arr);
        diaryService.saveDiary(diary);
        return true;
    }
    @RequestMapping("/getdiary/{ID}")
    public diary getdiary(@PathVariable int ID) {
        return diaryService.findById(ID);
    }
    @RequestMapping("/getimage/{ID}")
    public byte[] getimage(@PathVariable int ID) {
        ImageEntity imageEntity = diaryService.find_image_by_id(ID);
        String imagePath = imageEntity.getImagepath();
        File file = new File(imagePath);
        byte[] buffer = new byte[(int) file.length()];
        if (file.exists() && file.canRead()) { // 检查文件是否存在且可读
            try (FileInputStream fis = new FileInputStream(file)) { // 使用 try-with-resources 自动关闭资源
                fis.read(buffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace(); // 处理文件未找到异常
            } catch (IOException e) {
                e.printStackTrace(); // 处理 IO 异常
            }
        }
        return buffer; // 返回字节数组
    }
    @RequestMapping("/getcontent/{ID}")
    public ContentEntity getcontent(@PathVariable int ID) {
        return diaryService.find_content_by_id(ID);
    }
    @RequestMapping("/saveImage")
    public void saveImage(@RequestParam byte[] imageData) {
        int imageID = diaryService.find_image_max_id() + 1;
        String imagePath = IMAGE_STORAGE_DIR +imageID+"jpg";
        try (FileOutputStream fos = new FileOutputStream(imagePath)){
            fos.write(imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int maxID = diaryService.find_image_max_id() + 1;
        ImageEntity imageEntity = new ImageEntity(maxID, imagePath);
    }
    @RequestMapping("/test_add")
    public String test_add() {
        int image_num =1;
        String image_path="src/main/resources/static/images/1.jpg";
        String title="巴黎";
        String description="巴黎";
        int userID = 1;
        String content="实验内容";
        int contentID = diaryService.find_content_max_id() + 1;
        ContentEntity contentEntity = new ContentEntity(contentID, content);
        diaryService.saveContent(contentEntity);
        int diaryID = diaryService.find_image_max_id() + 1;
        int imageID = diaryService.find_image_max_id() + 1;
        for (int i = 0; i < image_num; i++) {
            ImageEntity imageEntity = new ImageEntity(imageID, image_path);
            diaryService.saveImage(imageEntity);
            imageID++;
        }
        int[]arr=new int[image_num];
        imageID-=image_num;
        for (int i = 0; i < image_num; i++) {
            arr[i]=imageID;
            imageID++;
        }
        diary diary = new diary(diaryID, title, userID, description, contentID,arr);
        diaryService.saveDiary(diary);
        return "success";
    }
    @RequestMapping("/user/check/{userID}/{diaryID}")
    public String check(int userID,int diaryID) {
        return "5";
    }
    public static byte[] convertImageToByteArray(String imagePath) {
        File imageFile = new File(imagePath);
        try (FileInputStream fis = new FileInputStream(imageFile);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String generateRandomContent() {
        String[] sentences = {
                "今天天气真好，阳光明媚，心情格外舒畅。",
                "这次旅行真是一次难忘的经历，看到了许多美丽的风景。",
                "和朋友们一起度过了愉快的时光，留下了美好的回忆。",
                "这个地方的美食太好吃了，让人回味无穷。"
        };
        Random random = new Random();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            content.append(sentences[random.nextInt(sentences.length)]).append(" ");
        }
        return content.toString();
    }

    // 模拟生成随机的目的地
    private String generateRandomDestination() {
        String[] destinations = {
                "巴黎", "纽约", "东京", "伦敦", "悉尼"
        };
        Random random = new Random();
        return destinations[random.nextInt(destinations.length)];
    }

    // 为每张图片生成日记
    @RequestMapping("/generateDiariesForImages")
    public String generateDiariesForImages() {
        File directory = new File(IMAGE_STORAGE_DIR);
        File[] files = directory.listFiles();
        for (int i = 1; i <= 94; i++) {
            String fileName = i + ".jpg";
            File file = new File(IMAGE_STORAGE_DIR + fileName);
            if (file.exists() && file.isFile()) {
                // 生成日记信息
                int contentID = diaryService.find_content_max_id() + 1;
                String content = generateRandomContent();
                ContentEntity contentEntity = new ContentEntity(contentID, content);
                diaryService.saveContent(contentEntity);

                int diaryID = diaryService.find_image_max_id() + 1;
                int imageID = diaryService.find_image_max_id() + 1;
                ImageEntity imageEntity = new ImageEntity(imageID, file.getAbsolutePath());
                diaryService.saveImage(imageEntity);

                int[] arr = {imageID};
                String title = "日记 - " + file.getName();
                String description = "关于 " + file.getName() + " 的日记";
                int userID = 1; // 假设用户 ID 为 1
                String destination = generateRandomDestination();
                diary diary = new diary(diaryID, title, userID, description, contentID, arr);
                diaryService.saveDiary(diary);
            }
        }
        return "所有图片的日记已生成";
    }


}
