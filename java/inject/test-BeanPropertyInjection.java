// License: LGPL-3.0 License (c) find-sec-bugs
// scaffold: dependencies=commons-beanutils.commons-beanutils@1.9.4
package inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanPropertyInjection {

    public void danger(Object bean, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        HashMap map = new HashMap();
        map.put("test", request.getParameter("test"));
        BeanUtils.populate(bean, map);
    }

    public void danger2(Object bean, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        Map map = new HashMap();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            map.put(name, request.getParameterValues(name));
        }
        BeanUtils.populate(bean, map);
    }

    public void danger3(Object bean, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        Map map = new HashMap();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            map.put(name, request.getParameterValues(name));
        }
        new BeanUtilsBean().populate(bean, map);
    }
}
