package bank.blog.service.search;

import bank.blog.remote.common.RemoteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchServiceSelector {

    private List<RemoteSearchService> selectors;

    @Autowired
    public SearchServiceSelector(final List<RemoteSearchService> allSearchServices) {
        this.selectors = allSearchServices;
    }

    public List<RemoteSearchService> getAllSearchServices() {
        return selectors;
    }

}
