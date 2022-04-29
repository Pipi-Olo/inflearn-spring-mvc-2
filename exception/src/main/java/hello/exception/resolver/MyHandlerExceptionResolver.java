package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

                // Exception 이 API 에서 터지면 WAS 에서 500에러가 터짐
                // Exception 을 sendError 로 변경해서 에러 번호를 4xx로 변경이 가능함.

                // 비어있기 때문에, 뷰 렌더링 없는 정상 흐름으로 변경
                // WAS 에서는 sendError()가 호출되었기 때문에 오류로 판단하고
                // 오류 번호는 BAD_REQUEST 400 으로 변경됨
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
