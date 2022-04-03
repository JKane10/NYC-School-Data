package com.jkane.a04042020_joshkane_nycschools.ui.schoollist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.jkane.a04042020_joshkane_nycschools.BuildConfig
import com.jkane.a04042020_joshkane_nycschools.R
import com.jkane.a04042020_joshkane_nycschools.SchoolActivity
import com.jkane.a04042020_joshkane_nycschools.app.App
import com.jkane.a04042020_joshkane_nycschools.app.utils.StringUtils
import com.jkane.a04042020_joshkane_nycschools.databinding.SchoolListFragmentBinding
import com.jkane.a04042020_joshkane_nycschools.models.NYCSchool
import com.jkane.a04042020_joshkane_nycschools.network.api.GooglePlacesAPI
import com.jkane.a04042020_joshkane_nycschools.network.repositories.GooglePlacesRepository
import com.jkane.a04042020_joshkane_nycschools.network.repositories.NYCSchoolsRepository
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListConstants.Companion.ADDRESS
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListConstants.Companion.EMAIL
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListConstants.Companion.PHONE
import com.jkane.a04042020_joshkane_nycschools.ui.schoollist.SchoolListConstants.Companion.WEBSITE
import com.skydoves.landscapist.glide.GlideImage
import javax.inject.Inject


/**
 * Fragment containing a list of Schools controlled by #[SchoolListRecyclerAdapter]
 */
class SchoolListFragment : Fragment() {
    private lateinit var mViewModel: SchoolListViewModel
    private lateinit var binding: SchoolListFragmentBinding
    private lateinit var adapter: SchoolListRecyclerAdapter

    @Inject
    lateinit var schoolRepo: NYCSchoolsRepository

    @Inject
    lateinit var placesRepo: GooglePlacesRepository

    @Inject
    lateinit var stringUtils: StringUtils

    @Inject
    lateinit var photoAPI: GooglePlacesAPI

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SchoolListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(SchoolListViewModel::class.java)
        setupView()
        setupObservers()
        setupFilter()
        if (mViewModel.schools.value == null) mViewModel.setInitialState(schoolRepo, placesRepo)
    }

    /**
     * Setups the views.
     *
     * @param composeEnabled - Boolean instructing method whether or not to display a recyclerview
     * or compose elements.
     */
    private fun setupView(composeEnabled: Boolean = BuildConfig.COMPOSE_VIEW) {
        if (composeEnabled) {
            binding.schoolListRecycler.visibility = View.GONE
            binding.schoolListComposeRoot.setContent {
                AppCompatTheme {
                    SchoolList(mViewModel.filteredSchoolsState)
                }
            }
        } else {
            binding.schoolListComposeRoot.visibility = View.GONE
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        adapter = SchoolListRecyclerAdapter(
            mViewModel.filteredSchools.value,
            stringUtils,
            photoAPI
        )
        binding.schoolListRecycler.setHasFixedSize(true)
        binding.schoolListRecycler.layoutManager = LinearLayoutManager(context)
        binding.schoolListRecycler.adapter = adapter
        adapter.setOnItemClickListener { position, _ ->
            (activity as SchoolActivity).showSchoolDetails(
                adapter.list[position], ""
            )
        }
    }

    private fun setupObservers() {
        observeLoading()
        observeError()
        if (!BuildConfig.COMPOSE_VIEW) observeFilteredSchools()
    }

    /**
     * Observes isLoading on the ViewModel and reaches out to parent activity to
     * render a loading overlay accordingly.
     *
     * This tightly couples this fragment to MainActivity.
     * TODO Decouple this by creating a base fragment or wrapper fragment.
     *
     * Loading state is currently being controlled by #[MainViewModel#loadSchools()].
     */
    private fun observeLoading() {
        mViewModel.isLoading.observe(
            viewLifecycleOwner
        ) { isLoading: Boolean? -> (requireActivity() as SchoolActivity).showLoading(isLoading) }
    }

    /**
     * Observes error on the ViewModel and reaches out to parent activity to
     * render a snackbar containing a user friendly error message if one is posted.
     *
     * This tightly couples this fragment to MainActivity.
     * TODO Decouple this by creating a base fragment or wrapper fragment.
     *
     * Error contains an int that represents a string value and will need to be provided
     * context in order to access the resource id. This is for ease of localization and
     * keeping all string values in the resource file.
     */
    private fun observeError() {
        mViewModel.error.observe(
            viewLifecycleOwner
        ) { error: Int? ->
            (requireActivity() as SchoolActivity).showError(
                getString(
                    error!!
                )
            )
        }
    }

    /**
     * Observes a filtered school list in the viewmodel.
     * Re-establishes the recyclerview when the list of filtered schools changes.
     */
    private fun observeFilteredSchools() {
        mViewModel.filteredSchools.observe(viewLifecycleOwner) { schools ->
            adapter.updateData(
                schools
            )
        }
    }

    private fun setupFilter() {
        binding.clearSearchIcon.setOnClickListener { binding.searchInput.setText("") }
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mViewModel.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): SchoolListFragment {
            return SchoolListFragment()
        }
    }

    /**
     *  ********************************************************************************************
     *
     * Composable functionality beyond this point. This is behind a build config flag [COMPOSE_VIEW]
     *
     * ********************************************************************************************
     */

    class SchoolPreviewParameterProvider : PreviewParameterProvider<List<NYCSchool>> {
        override val values = sequenceOf(
            listOf(NYCSchool(name = "abc"))
        )
    }

    @Preview(heightDp = 500, widthDp = 500)
    @Composable
    private fun SchoolList(
        @PreviewParameter(SchoolPreviewParameterProvider::class) schools: List<NYCSchool>?
    ) {
        LazyColumn {
            schools?.forEach { school ->
                item {
                    SchoolRow(school)
                }
            }
        }
    }

    /**
     * Composable Card that displays the School's name and details.
     *
     *
     *
     * @param school - The school that is being displayed.
     */
    @Composable
    private fun SchoolRow(school: NYCSchool) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .clickable(onClick = {
                    (activity as SchoolActivity).showSchoolDetails(
                        school,
                        mViewModel.schoolImageUrlMap[school]
                    )
                })
        ) {
            Row {
                if (BuildConfig.PLACES_API_ENABLED) {
                    SchoolImage(school)
                }
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = school.name ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    SchoolDetailRow(
                        detailType = WEBSITE,
                        detailValue = stringUtils.valueOrUnavailable(school.website ?: "")
                    )
                    SchoolDetailRow(
                        detailType = PHONE,
                        detailValue = stringUtils.valueOrUnavailable(school.phoneNumber)
                    )
                    SchoolDetailRow(
                        detailType = EMAIL,
                        detailValue = stringUtils.valueOrUnavailable(school.email)
                    )
                    SchoolDetailRow(
                        detailType = ADDRESS,
                        detailValue = stringUtils.addressOrUnavailable(school.location)
                    )
                }
            }
        }
    }

    @Composable
    private fun SchoolImage(school: NYCSchool) {
        if (!mViewModel.schoolImageUrlMap.containsKey(school)) {
            mViewModel.getImageUrlFromSchool(school)
        } else {
            val imageUrl = mViewModel.schoolImageUrlMap[school]
            if (imageUrl.isNullOrBlank()) return
            GlideImage(
                imageModel = imageUrl,
                contentDescription = "School Image",
                modifier = Modifier
                    .padding(start = 8.dp, top = 16.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
            )
        }
    }

    /**
     * Composable row representing a single school's details minus the name. This is flexible so we
     * can later add additional details and types if we want.
     *
     * @param detailType - The type of details the row will display
     * @param detailValue - The value to be displayed next to the label
     */
    @Composable
    private fun SchoolDetailRow(detailType: String, detailValue: String) {
        Row {
            Text(
                text = when (detailType) {
                    WEBSITE -> stringResource(R.string.website)
                    PHONE -> stringResource(R.string.phone)
                    EMAIL -> stringResource(R.string.email)
                    ADDRESS -> stringResource(R.string.address)
                    else -> ""
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = detailValue, fontSize = 12.sp)
        }
    }
}