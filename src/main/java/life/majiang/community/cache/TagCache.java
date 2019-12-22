package life.majiang.community.cache;

import life.majiang.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","c","c++","php","perl","python","javascript","c#","ruby","go","lua","node.js","erlang","scala","bash","actionscript","golang","typescript","flutter"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django", "flash", "yii", "ruby-on-rails","tornado","koa","struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","sqlite","oracle","sql","nosql","redis","mongodb","memcached","postgresql"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("vim","emacs","ide","eclipse","xcode","intellij-idea","textmate","sublime-text","visual-studio","git","github","svn","hg","docker","ci"));
        tagDTOS.add(tool);

//        TagDTO sex = new TagDTO();
//        sex.setCategoryName("敏感");
//        sex.setTags(Arrays.asList("JJ","大JJ","B","大B"));
//        tagDTOS.add(sex);

        return tagDTOS;
    }

    //拿到用户输入的和标签页里的不同标签，然后提示用户此标签不能用
    public static String checkInvalid(String tag){
        String[] split = StringUtils.split(tag, ",");
        List<TagDTO> tagDTOS = get();
        List<String> collect = tagDTOS.stream().flatMap(tags -> tags.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !collect.contains(t)).collect(Collectors.joining());
        return invalid;
    }
}
