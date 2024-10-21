package com.dogs.picturesByBreed.units;

import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoPictureForBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoSuchBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.models.dogsApi.BreedImagesResponse;
import com.dogs.picturesByBreed.business.models.dogsApi.ListAllBreedsResponse;
import com.dogs.picturesByBreed.business.services.DogsService;
import com.dogs.picturesByBreed.business.validationServices.DogsValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class DogValidationServiceTests {
    @Mock
    private DogsService dogsService;
    @InjectMocks
    private DogsValidationService dogsValidationService;

    @Test
    public void invalidBreedThrowsNoSuchBreedExceptionTest() {
        var mockedListAllBreeds = new ListAllBreedsResponse(
                new HashMap<>(),
                "fail"
        );
        Mockito.when(dogsService.getAllBreeds()).thenReturn(mockedListAllBreeds);

        assertThrows(NoSuchBreedDogsApiResponseException.class,
                () -> dogsValidationService.validateBreed("invalidBreed")
        );
        Mockito.verify(dogsService).getAllBreeds();
        Mockito.verifyNoMoreInteractions(dogsService);
    }

    @Test
    public void breedWithNoPicutresThrowsNoPictureForBreedExceptionTest() {
        var mockedListAllBreeds = new ListAllBreedsResponse(
                Map.of("some breed", new ArrayList<>()),
                "success"
        );
        var mockedBreedImages = new BreedImagesResponse(
                new ArrayList<>(),
                "fail"
        );
        Mockito.when(dogsService.getAllBreeds()).thenReturn(mockedListAllBreeds);
        Mockito.when(dogsService.getBreedImages("some breed")).thenReturn(mockedBreedImages);

        assertThrows(NoPictureForBreedDogsApiResponseException.class,
                () -> dogsValidationService.validateBreed("some breed")
        );
        Mockito.verify(dogsService).getAllBreeds();
        Mockito.verify(dogsService).getBreedImages("some breed");
        Mockito.verifyNoMoreInteractions(dogsService);
    }

    @Test
    public void breedWithPicturesDoNotThrowsExceptionsTest() throws NoPictureForBreedDogsApiResponseException, NoSuchBreedDogsApiResponseException {
        var mockedListAllBreeds = new ListAllBreedsResponse(
                Map.of("some breed", new ArrayList<>()),
                "success"
        );
        var mockedBreedImages = new BreedImagesResponse(
                List.of("some image"),
                "success"
        );
        Mockito.when(dogsService.getAllBreeds()).thenReturn(mockedListAllBreeds);
        Mockito.when(dogsService.getBreedImages("some breed")).thenReturn(mockedBreedImages);

        dogsValidationService.validateBreed("some breed");

        Mockito.verify(dogsService).getAllBreeds();
        Mockito.verify(dogsService).getBreedImages("some breed");
        Mockito.verifyNoMoreInteractions(dogsService);
    }
}
