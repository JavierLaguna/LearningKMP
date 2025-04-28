import Foundation
import SwiftUI
import ComposeApp

struct DetailScreen: View {
    @StateObject var viewModelStoreOwner: SharedViewModelStoreOwner<DetailViewModel>
    
    init(id: Int32) {
        self._viewModelStoreOwner = StateObject(
            wrappedValue: SharedViewModelStoreOwner(DetailViewModel(movieId: id))
        )
    }
    
    var body: some View {
        Observing(viewModelStoreOwner.instance.state) { state in
            VStack {
                if state.isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                }
                
                if let movieDetail = state.movie {
                    MovieDetail(
                        movie: movieDetail,
                        onFavoriteClick: { viewModelStoreOwner.instance.onFavoriteClick() }
                    )
                }
            }
        }
    }
}

private struct MovieDetail: View {
    let movie: Movie
    let onFavoriteClick: () -> Void
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                if let backdrop = movie.backdrop {
                    AsyncImage(url: URL(string: backdrop)) { image in
                        image
                            .resizable()
                            .frame(maxWidth: .infinity)
                    } placeholder: {
                        ProgressView()
                    }
                    .aspectRatio(16 / 9, contentMode: .fill)
                    .frame(maxHeight: 200)
                    .clipped()
                }
                
                Text(movie.overview)
                    .padding()
                
                VStack(alignment: .leading, spacing: 8) {
                    Group {
                        Text("**Original language**: \(movie.originalLanguage)")
                        
                        Text("**Original title**: \(movie.originalTitle)")
                            
                        Text("**Release date**: \(movie.releaseDate)")
                            
                        Text("**Popularity**: \(movie.popularity)")
                            
                        Text("**Vote average**: \(movie.voteAverage)")
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                }
                .padding()
                .background(.secondary.opacity(0.1))
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .leading)
                .cornerRadius(8)
            }
        }
        .navigationTitle(movie.title)
        .navigationBarItems(trailing: Button(action: onFavoriteClick) {
            Image(systemName: movie.isFavorite ? "heart.fill" : "heart")
        })
    }
}
