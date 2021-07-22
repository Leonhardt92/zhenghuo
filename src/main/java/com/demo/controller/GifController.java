package com.demo.controller;

import com.demo.dao.VedioDao;
import com.demo.model.Form;
import com.demo.service.GifService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by lizhihao on 2018/3/11.
 */
@Api(value = "整活",tags= {"整活"})
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/")
public class GifController {

    @Autowired
    GifService gifService;

    @Autowired
    VedioDao vedioDao;

    @ApiOperation(value = "获取gif", notes = "")
    @RequestMapping(path = "/gif/filePath", produces = {MediaType.APPLICATION_JSON_VALUE},method = RequestMethod.POST,consumes = { "multipart/form-data" })
    public Map renderGifPath(@ModelAttribute Form form) throws Exception {
        ConcurrentMap<String, String> map = Maps.newConcurrentMap();

        String file = gifService.renderGif(form);
        String filePath = Paths.get(file).getFileName().toString();
        map.put("code", "0");
        map.put("result", filePath);
        return map;
    }

    @ApiOperation(value = "获取表情包模板有多少个脸", notes = "")
    @RequestMapping(path = "/gif/getFaceCount/{templateName}", method = RequestMethod.GET)
    public int getFaceCount(@PathVariable("templateName") String templateName){
        return vedioDao.countFaces(templateName);
    }


    @ApiOperation(value = "获取字幕", notes = "")
    @RequestMapping(path = "/gif/getSubtitles/{templateName}", method = RequestMethod.GET)
    public List<String> getSubtitles(@PathVariable("templateName") String templateName) {
        return vedioDao.getSubtitles(templateName);
    }

    @ApiOperation(value = "获取所有表情包模板", notes = "")
    @RequestMapping(path = "/gif/getVedio", method = RequestMethod.GET)
    public List<String> getVedios() {
        return vedioDao.getAllVedios();
    }

    @ApiOperation(value = "直接获取表情包", notes = "")
    @RequestMapping(path = "/gif/file", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<Resource> renderGif(@RequestBody Form subtitles) throws Exception {
        String file = gifService.renderGif(subtitles);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=txtx.gif").body(resource);
    }

}
