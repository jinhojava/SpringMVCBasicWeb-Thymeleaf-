package springmvcweb.mvcitemservice.domain.item;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static //실무에선ConcurrentHashMap
    private static long sequence = 0L; //static   //automiclong실무

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;

    }
    public Item findById(Long id){
        return store.get(id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(store.values());  //store값 모음 반환
    }
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    public void clearStore(){
        store.clear();
    }

}
