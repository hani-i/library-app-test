package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.book.UserLoanHistoryRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;

    private final UserRepository userRepository;

    //스프링 빈으로 설정하면 생성자가 생성될 때 알아서 찾아줌
    public BookService(BookRepository bookRepository,
                       UserLoanHistoryRepository userLoanHistoryRepository,
                       UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }


    @Transactional
    public void loanBook(BookLoanRequest request){
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            throw new IllegalArgumentException("대출되어 있는 책입니다.");
        }

        User user = userRepository.findByName(request.getUserName()).orElseThrow(IllegalArgumentException::new);
        //bookRepository.user.save(new UserLoanHistory(request.getUserName(),request.getBookName()));
        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName(), false));
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        //유저 찾기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName()).orElseThrow(IllegalArgumentException::new);
        history.doReturn(); //트랜잭션을 통해 영속성 컨텍스트가 변경감지 기능을 통해 변경해줌

//        Book book = bookRepository.findByName(request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);

    }
}
