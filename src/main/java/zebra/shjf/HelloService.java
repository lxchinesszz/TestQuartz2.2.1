package zebra.shjf;

import org.springframework.stereotype.Service;

/**
 * Created by liuxin on 16/12/24.
 */
@Service
public class HelloService {
    static int i=0;
    public void hh(){
        System.out.println(++i);
    }
}
