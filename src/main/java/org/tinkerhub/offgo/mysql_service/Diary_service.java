package org.tinkerhub.offgo.mysql_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinkerhub.offgo.Repository.ContentRepository;
import org.tinkerhub.offgo.Repository.ImageRepository;
import org.tinkerhub.offgo.Repository.UserRepository;
import org.tinkerhub.offgo.Repository.DiaryRepository;
import org.tinkerhub.offgo.entity.ContentEntity;
import org.tinkerhub.offgo.entity.ImageEntity;
import org.tinkerhub.offgo.entity.diary;

import java.util.List;


@Service
public class Diary_service {
    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ContentRepository contentRepository;
    public Diary_service() {

    }
    public int find_content_max_id() {
        if (contentRepository.findAll().isEmpty()) {
            return 0;
        }

        return contentRepository.findMaxID();
    }
    public int find_image_max_id() {
        if (imageRepository.findAll().isEmpty()) {
        return 0;
    }

        return imageRepository.findMaxID();
    }
    public diary findById(int id) {
        return diaryRepository.findById(id);
    }
    public ContentEntity find_content_by_id(int id) {
        return contentRepository.findById(id);
    }
    public ImageEntity find_image_by_id(int id) {
        return imageRepository.findById(id);
    }
    public diary saveDiary(diary diary) {
        return diaryRepository.save(diary);
    }
    public ImageEntity saveImage(ImageEntity imageEntity) {
        return imageRepository.save(imageEntity);
    }
    public ContentEntity saveContent(ContentEntity contentEntity) {
        return contentRepository.save(contentEntity);
    }
    public void cleanDiaryData() {
        // 清空 diary 表的数据
        diaryRepository.deleteAll();
        // 清空 ContentEntity 表的数据
        contentRepository.deleteAll();
        // 清空 ImageEntity 表的数据
        imageRepository.deleteAll();
    }
    public List<diary> getAllDiaries() {
        return diaryRepository.findAll();
    }
}
