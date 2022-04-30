package demo.mapper;

import demo.domain.SetPlace;
import demo.domain.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    String getNewCode(Integer userCodeNo, String today);
    Integer insertTripPlace(SetPlace place);

    List<Integer> getCategoryNoList(List<String> placeCategory);
    Integer insertCategory(Integer tripPlaceNo, Integer categoryNo);

    Tag getTag(String tagName);
    Integer insertPlaceTag(Integer seqNo, Integer tagNo);
    Integer insertTag(Tag tag);
}
