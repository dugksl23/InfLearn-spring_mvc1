package hello.Itemservice.repository;

import hello.Itemservice.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemRepositoryTest {


    private ItemRepository store = new ItemRepository();

    @AfterEach
    void init() {
        store.clearStore();
    }

    @Test
    void save() {

        //  ...given
        Item item = new Item();
        item.setItemName("item1");
        item.setPrice(1000);
        item.setQuantity(10);

        // ... when
        Item save = store.save(item);

        // ...then
        Assertions.assertThat(save).isInstanceOf(Item.class);
        Assertions.assertThat(save.getId()).isEqualTo(1);

    }

    @Test
    void findOne() {

        //  ...given
        Item item = new Item();
        item.setItemName("item2");
        item.setPrice(2000);
        item.setQuantity(20);
        Item save = store.save(item);

        // ... when
        Item one = store.findOne(save.getId());

        // ...then
        Assertions.assertThat(save.getId()).isEqualTo(one.getId());

    }

    @Test
    void deleteOne() {

        //  ...given
        Item item = new Item();
        item.setItemName("item2");
        item.setPrice(2000);
        item.setQuantity(20);
        Item save = store.save(item);

        // ... when
        Item delete = store.delete(save.getId());

        // ...then
        Assertions.assertThat(save.getId()).isEqualTo(delete.getId());
        assertThrows(NullPointerException.class, () -> {
            Item one = store.findOne(save.getId());
            System.out.println("delete one : " + one.getId());
        });

    }

    @Test
    void findAll() {


        // ..given
        Item item = new Item("1", 1000, 10);
        Item item2 = new Item("2", 1000, 10);
        List<Item> collect = stream(Arrays.array(item, item2)).collect(Collectors.toList());
        List<Item> items = store.saveAll(collect);
//        items.add(new Item("3", 1000, 10));

        // ..when
        List<Item> all = store.findAll(); // -> callby reference 깊은 복사 와 얕은 복

        // ..then
        Assertions.assertThat(items).isEqualTo(all);


    }

    @Test
    void update() {

        // ..given
        Item item = new Item("1", 1000, 10);
        Item item1 = store.save(item);
        item1.setItemName("ddd");
        item1.setPrice(2000);

        // ..when
        Item one = store.findOne(item.getId());

        // ..then
        Assertions.assertThat(one.getItemName()).isEqualTo(item1.getItemName());

    }


    @Test
    void copTest() {

        // 깊은 복사
        int[] arr = new int[]{0, 1, 2, 3, 4, 5};
        int[] clone = arr.clone();
        clone[0] = 1;
        System.out.println(arr[0]);
        System.out.println(clone[0]);

        int a = 10;
        int b = a;
        b = 11;

        System.out.println(a);
        System.out.println(b);


        List<Item> items = new ArrayList<>();
        items.add(new Item("10",1,1));

        List<Item> collect = new ArrayList<>();
        collect.addAll(items);
        collect.get(0).setPrice(20);
        System.out.println(collect.get(0).getPrice());
        System.out.println(items.get(0).getPrice());

        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(collect.get(0).getPrice());
        // 얕은 복사

    }

}