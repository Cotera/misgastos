package com.app.misgastos.services.impl;

import com.app.misgastos.model.AccountDto;
import com.app.misgastos.model.entities.AccountEntity;
import com.app.misgastos.repository.AccountRepository;
import com.app.misgastos.utils.converters.AccountConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest //Con esta anotación levantamos un contexto de spring para que la clase a testear recoga los objetos marcados con el @Autowired de los que indiquemos aqui con el @Mock, y asi poder emular las llamadas
public class AccountServiceImplTest {

    // esta clase se instanciatá de forma emulada (mock)
    @Mock
    private AccountRepository accountRepository;

    // instanciaremos esta clase haciendo que use los elementos que hemos indicado emulados
    @InjectMocks
    private AccountServiceImpl accountService;


    @Before //Antes de iniciar los test se ejecuta lo que haya dentro del método masrcado con @Before
    public void inicio() {
        // Es necesario inicializar los mocks con JUnit 5 esto ya no es necesario
        MockitoAnnotations.openMocks(this);
    }

    @Test // Los metodos masrcados con @Test son los que se ejcutaran con nuestros test.
    public void createAccount() {
    }

    @Test
    public void getById() {
        // ARRANGE (Given)
        Long givenId = 1L;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setName("Cuenta Banco España");
        accountEntity.setCurrency("EUR");

        Optional<AccountEntity> accountEntityO = Optional.of(accountEntity);
        when(accountRepository.findById(givenId)).thenReturn(accountEntityO);

        // TEST (when)
        Optional<AccountDto> actualAccount = accountService.getById(givenId);

        // ASSERT (then)
        assertTrue(actualAccount.isPresent());
        assertEquals(1L, actualAccount.get().getId().longValue());
        assertEquals("Cuenta Banco España", actualAccount.get().getName());
        assertEquals(Currency.getInstance("EUR"), actualAccount.get().getCurrency());
    }

    @Test
    public void getAll() {
        // Given
        List<AccountEntity> accountEntities = getAccountEntitiesFromRepositoryGetAll();
        when(accountRepository.findAll())
                .thenReturn(accountEntities);
        List<AccountDto> expectedList = getAllExpectedDtos(); // creamos un método tipo private (solo se puede invocar desde esta clase) para crear la lista me hacer el método más ententible
        // When
        List<AccountDto> actualAccounts = accountService.getAll();
        // Then
        assertEquals(expectedList, actualAccounts);
    }

    @Test
    public void deleteById() throws Exception {
        // given
        Long givenId = 12L; // el valor del long no afecta al test, son mis datos de prueba y puedo poner los valores que me convengan

        AccountEntity entity = new AccountEntity();
        entity.setId(12L); //es conveniente que los datos tengan cierta lógica.
                            // Este dato lo devolvemos con el findById de forma emulada por lo que no tiene sentido que el id sea otro cuando pedimos que busque el id= 12.
        when(accountRepository.findById(givenId))
                .thenReturn(Optional.of(entity));

        doNothing().when(accountRepository).deleteById(givenId); //dado que delete es void, le decimos simplemente que no haga nada cuando invoque al delete del repositorio
                    // ¡¡¡ OJO !!! acuerdate de que dentro del when(...) solo va el nombre del repositorio, el método del repositorio al que llamas va fuera del When.
        // when
        accountService.deleteById(givenId);

        // then
        verify(accountRepository).deleteById(givenId);
        verifyNoMoreInteractions();
        // Mañana te recalco esto en clase, pero al no tener datos del delete para hacer assertions, simplemente verificamos que el metodo delete del repositorio es llamado
    }

    @Test (expected = Exception.class) // debemnos indicar el tipo de excepcion esperado, ya que si hay otra excepcion difetenre (como NullPointerException) el test dará incorrecto.
    public void deleteById2() throws Exception {
        // given
        Long givenId = 12L; // el valor del long no afecta al test, son mis datos de prueba y puedo poner los valores que me convengan

        when(accountRepository.findById(givenId))
                .thenReturn(Optional.empty());

        // when
        accountService.deleteById(givenId);

        // then
    }

    @Test
    public void update() {
    }

    private List<AccountEntity> getAccountEntitiesFromRepositoryGetAll() {
        AccountEntity entity1 = new AccountEntity();
        entity1.setId(1L);
        entity1.setName("Santander");

        AccountEntity entity2 = new AccountEntity();
        entity2.setId(2L);
        entity2.setName("Caixa");


        List<AccountEntity> accountEntities = new ArrayList<>();
        accountEntities.add(entity1);
        accountEntities.add(entity2);
        return accountEntities;
    }

    private List<AccountDto> getAllExpectedDtos() {
        AccountDto account1 = new AccountDto();
        account1.setId(1L);
        account1.setName("Santander");
        AccountDto account2 = new AccountDto();

        account2.setId(2L);
        account2.setName("Caixa");
        List<AccountDto> expectedList = new ArrayList<>();
        expectedList.add(account1);
        expectedList.add(account2);
        return expectedList;
    }
}