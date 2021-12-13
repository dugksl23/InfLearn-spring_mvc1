package hello.Itemservice.repository;


import hello.Itemservice.domain.item.Item;
import org.springframework.stereotype.Repository;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> itemRepository = new ConcurrentReferenceHashMap<>(); // multiThread 환경을 위한 동시성을 위해 ConCurrent
    private static AtomicLong seq = new AtomicLong();//static 은 처음 메모리에 올려두고 싱글턴처럼 사용하기 위함, new를 방지하여 prototype처럼 사용하지 못하게 하기 위함.


    public Item save(Item item) {
        item.setId(seq.incrementAndGet()); //final 일 경우 갑변경 못함.
        itemRepository.put(item.getId(), item);
        return item;
    }

    public Item findOne(Long seq) {
        return itemRepository.get(seq); // 얕은 복사 - 주소값 반환
    }

    public List<Item> findAll() {
        return new ArrayList<>(itemRepository.values()); // 깊은 복사 -> 유사객체 반환
    }

    public Item update(Long id, Item itemParam) {

        Item item = findOne(id);
        item.setItemName(itemParam.getItemName());
        item.setPrice(itemParam.getPrice());
        item.setQuantity(itemParam.getQuantity());

        return item;
    }

    public Item delete(Long id) {
        Item remove = itemRepository.remove(id);
        return remove;
    }

    public void clearStore() {
        itemRepository.clear();
    }

    public List<Item> saveAll(List<Item> items) {
        List<Item> collect = items.stream().map(item -> this.save(item)).collect(Collectors.toList());
        return collect;
    }
}
