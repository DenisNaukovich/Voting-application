package OTS.task.votingApp.controller;


import OTS.task.votingApp.Application;
import OTS.task.votingApp.model.VoteOption;
import OTS.task.votingApp.model.Voting;
import OTS.task.votingApp.repository.VoteOptionRepository;
import OTS.task.votingApp.repository.VotingRepository;
import OTS.task.votingApp.service.VoteOptionService;
import OTS.task.votingApp.service.VotingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class VotingRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<VoteOption> options = new ArrayList<>();
    private Voting voting;

    @Autowired
    private VotingRepository votingRepository;
    @Autowired
    private VoteOptionRepository voteOptionRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.voting = votingRepository.save(new Voting("Title", options));
        this.options.add(voteOptionRepository.save(new VoteOption("Option #1", voting)));
        this.options.add(voteOptionRepository.save(new VoteOption("Option #2", voting)));
        this.options.add(voteOptionRepository.save(new VoteOption("Option #3", voting)));
    }

    @After
    public void tearDown() throws Exception {
        options.forEach(option -> voteOptionRepository.delete(option));
        votingRepository.delete(voting);
    }

    @Test
    public void getVotings() throws Exception {
        this.mockMvc.perform(get("/voting/page/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void increaseVoteOption() throws Exception {
        String voteOptionIdJson = json(voting.getOptions().get(0).getId());

        this.mockMvc.perform(put("/voting/" + voting.getId() + "/vote")
                .contentType(contentType)
                .content(voteOptionIdJson))
                .andExpect(status().isCreated());

        if (voting.getOptions().get(0).getCounterVotes()+1 != voteOptionRepository.findOne(voting.getOptions().get(0).getId()).getCounterVotes())
            throw new Exception();
    }

    @Test
    public void closeVoting() throws Exception {
        this.mockMvc.perform(put("/voting/" + voting.getId() + "/close"))
                .andExpect(status().isCreated());

        if (!votingRepository.findOne(voting.getId()).isClosed())
            throw new Exception();
    }

    @Test
    @Transactional
    public void createVoting() throws Exception {
        String votingJson = json(new Voting("adding title", Arrays.asList(new VoteOption("name #1"), new VoteOption("name #2"))));

        this.mockMvc.perform(post("/voting/add")
                .contentType(contentType)
                .content(votingJson))
                .andExpect(status().isOk());

    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
